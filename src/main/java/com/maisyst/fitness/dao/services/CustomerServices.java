package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.services.interfaces.*;
import com.maisyst.fitness.dao.repositories.ICustomerRepository;
import com.maisyst.fitness.models.CustomerSubscribeResponse;
import com.maisyst.fitness.models.SubscribeResponse;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CustomerModel;
import com.maisyst.fitness.models.SubscribeModel;
import com.maisyst.fitness.utils.MaiUID;
import com.maisyst.fitness.utils.MaiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.maisyst.fitness.utils.MaiUtils.getDateSubscription;
import static com.maisyst.fitness.utils.MaiUtils.stringToTypeSubscription;

@Service
public class CustomerServices implements ICustomerServices {
    private final ICustomerRepository customerRepository;
    private final ISubscribeServices subscribeServices;
    private final ISubscriptionServices subscriptionServices;
    private final IRoomServices roomServices;

    private final IActivityServices activityServices;
    private final JdbcTemplate jdbcTemplate;

    public CustomerServices(ICustomerRepository customerRepository, ISubscribeServices subscribeServices,
                            ISubscriptionServices subscriptionServices, IRoomServices roomServices, IActivityServices activityServices, JdbcTemplate jdbcTemplate) {
        this.customerRepository = customerRepository;
        this.subscribeServices = subscribeServices;
        this.subscriptionServices = subscriptionServices;
        this.roomServices = roomServices;
        this.activityServices = activityServices;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MaiResponse<CustomerModel> insert(CustomerModel model) {
        return null;
    }

    @Override
    public MaiResponse<CustomerModel> insertWithSubscription(String typeSubscription, String activity_id, String roomId, CustomerModel model) {
        try {
            var subscriptionResponse = subscriptionServices.findByType(typeSubscription);
            var activityModelMaiResponse = activityServices.findById(activity_id);
            var roomModelMaiResponse = roomServices.findById(roomId);
            if (subscriptionResponse.getStatus() == HttpStatus.OK &&
                    activityModelMaiResponse.getStatus() == HttpStatus.OK &&
                    roomModelMaiResponse.getStatus() == HttpStatus.OK) {
                var customerResponse = customerRepository.save(
                        new CustomerModel(
                                model.getFirstName(),
                                model.getLastName(),
                                model.getYearOfBirth(),
                                model.getAddress(),
                                MaiUID.generate(),
                                roomModelMaiResponse.getData()
                        )
                );
                var moment = getDateSubscription(stringToTypeSubscription(typeSubscription));
                subscriptionResponse.getData().getActivities().add(activityModelMaiResponse.getData());
                var subscribeRes = subscribeServices.insert(new SubscribeModel(moment[0], moment[1], true, customerResponse, subscriptionResponse.getData()));
                if (subscribeRes.getStatus() == HttpStatus.OK) {
                    jdbcTemplate.update("INSERT INTO concern VALUES(?,?)", activity_id, subscriptionResponse.getData().getSubscriptionId());
                    return new MaiResponse.MaiSuccess<>(customerResponse, HttpStatus.OK);
                } else {
                    return new MaiResponse.MaiError<>(subscribeRes.getMessage(), HttpStatus.NOT_FOUND);
                }
            } else {
                var error = subscriptionResponse.getMessage() == null || subscriptionResponse.getMessage().isBlank() ? activityModelMaiResponse.getMessage() : subscriptionResponse.getMessage();
                return new MaiResponse.MaiError<>(error, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteById(String id) {
        try {
            var customerResponse = customerRepository.findById(id);
            if (customerResponse.isPresent()) {
                customerRepository.deleteById(id);
                return new MaiResponse.MaiSuccess<>("Customer have been deleted", HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Customer haven't been deleted", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<CustomerModel> findById(String id) {
        try {
            var customerResponse = customerRepository.findById(id);
            if (customerResponse.isPresent()) {
                return new MaiResponse.MaiSuccess<>(customerResponse.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Customer don't exist", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<CustomerSubscribeResponse> findByIdentityEMFAndRoom(String roomId, String identity) {
        try {
            var room = roomServices.findById(roomId);
            if (room.getStatus() == HttpStatus.OK) {
                var customerResponse = customerRepository.findByIdentityEMFAndRoom(identity, room.getData());
                if (customerResponse.isPresent()) {
                    var responseSubscribes = subscribeServices.findAllByCustomer(customerResponse.get());
                    if (responseSubscribes.getStatus() == HttpStatus.OK) {
                        final List<SubscribeResponse> subscribeResponse = new ArrayList<>();
                        responseSubscribes.getData().forEach(item -> {
                            if (item.getIsActive()) {
                                subscribeResponse.add(
                                        new SubscribeResponse(
                                                item.getSubscribeId(),
                                                item.getDateStart(),
                                                item.getDateEnd(),
                                                item.getIsActive(),
                                                item.getSubscription().getType().toString()
                                        )
                                );
                            }
                        });
                        var customerSubscribe = new CustomerSubscribeResponse(
                                customerResponse.get().getCustomerId(),
                                customerResponse.get().getIdentityEMF(),
                                customerResponse.get().getFirstName(),
                                customerResponse.get().getLastName(),
                                customerResponse.get().getYearOfBirth(),
                                customerResponse.get().getAddress(),
                                room.getData().getRoomName(),
                                subscribeResponse
                        );
                        var responseValidate = MaiUtils.checkValidateAllSubscribe(responseSubscribes.getData());
                        subscribeServices.insertMany(responseValidate);
                        return new MaiResponse.MaiSuccess<>(customerSubscribe, HttpStatus.OK);
                    } else {
                        return new MaiResponse.MaiError<>(responseSubscribes.getMessage(), responseSubscribes.getStatus());
                    }
                } else {
                    return new MaiResponse.MaiError<>("Customer don't exist", HttpStatus.NO_CONTENT);
                }
            } else {
                return new MaiResponse.MaiError<>(room.getMessage(), room.getStatus());
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<CustomerModel>> fetchAll() {
        return null;
    }

    @Override
    public MaiResponse<List<CustomerSubscribeResponse>> fetchAllWithSubscribes() {
        try {
            var response = customerRepository.findAll();
            final List<CustomerSubscribeResponse> data =
                    response.stream().map(item -> {
                        final List<SubscribeResponse> dataSubscribes = new ArrayList<>();
                        item.getSubscribes().forEach(it -> {
                            if (it.getIsActive()) {
                                dataSubscribes.add(new SubscribeResponse(
                                        it.getSubscribeId(),
                                        it.getDateStart(),
                                        it.getDateEnd(),
                                        it.getIsActive(),
                                        it.getSubscription().getType().toString()
                                ));
                            }

                        });
                        return new CustomerSubscribeResponse(
                                item.getCustomerId(),
                                item.getIdentityEMF(),
                                item.getFirstName(),
                                item.getLastName(),
                                item.getYearOfBirth(),
                                item.getAddress(),
                                item.getRoom().getRoomName(),
                                dataSubscribes
                        );
                    }).toList();
            return new MaiResponse.MaiSuccess<>(data, HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<CustomerSubscribeResponse>> fetchAllByRoom(String roomId) {
        try {
            var room = roomServices.findById(roomId);
            if (room.getStatus() == HttpStatus.OK) {
                var response = customerRepository.findAllByRoom(room.getData());
                List<CustomerSubscribeResponse> data = response.stream().map(item -> {

                    var responseSubscribes = subscribeServices.findAllByCustomer(item).getData();
                    final List<SubscribeResponse> subscribeResponses = new ArrayList<>();
                    responseSubscribes.forEach(subscribeModel -> {
                        if (subscribeModel.getIsActive()) {
                            subscribeResponses.add(new SubscribeResponse(
                                    subscribeModel.getSubscribeId(),
                                    subscribeModel.getDateStart(),
                                    subscribeModel.getDateEnd(),
                                    subscribeModel.getIsActive(),
                                    subscribeModel.getSubscription().getType().toString()
                            ));
                        }
                    });
                    return new CustomerSubscribeResponse(
                            item.getCustomerId(),
                            item.getIdentityEMF(),
                            item.getFirstName(),
                            item.getLastName(),
                            item.getYearOfBirth(),
                            item.getAddress(),
                            room.getData().getRoomName(),
                            subscribeResponses
                    );
                }).toList();

                return new MaiResponse.MaiSuccess<>(data, HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>(room.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> insertMany(List<CustomerModel> models) {
        return null;
    }

    @Override
    public MaiResponse<String> deleteMany(List<String> ids) {
        try {
            customerRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Customer has been deleted.", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<CustomerModel> update(String customerId, CustomerModel model) {
        try {
            var responseCustomer = customerRepository.findById(customerId);

            if (responseCustomer.isPresent()) {
                responseCustomer.get().setAddress(model.getAddress());
                responseCustomer.get().setFirstName(model.getFirstName());
                responseCustomer.get().setLastName(model.getLastName());
                responseCustomer.get().setYearOfBirth(model.getYearOfBirth());
                customerRepository.save(responseCustomer.get());
                return new MaiResponse.MaiSuccess<>(responseCustomer.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Customer not found.", HttpStatus.NOT_FOUND);
            }


        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public MaiResponse<CustomerModel> update(String customerId, String roomId, CustomerModel model) {
        try {
            var responseCustomer = customerRepository.findById(customerId);

            if (responseCustomer.isPresent()) {
                var room = roomServices.findById(roomId);
                responseCustomer.get().setAddress(model.getAddress());
                responseCustomer.get().setFirstName(model.getFirstName());
                responseCustomer.get().setLastName(model.getLastName());
                responseCustomer.get().setYearOfBirth(model.getYearOfBirth());
                responseCustomer.get().setRoom(room.getData());
                customerRepository.save(responseCustomer.get());
                return new MaiResponse.MaiSuccess<>(responseCustomer.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Customer not found.", HttpStatus.NOT_FOUND);
            }


        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<CustomerModel> resubscribe(String identity, String subscriptionId) {
        try {
            var responseCustomer = customerRepository.findByIdentityEMF(identity);
            if (responseCustomer.isPresent()) {
                var subsc = subscribeServices.findAllByCustomer(responseCustomer.get());
                if (subsc.getStatus() == HttpStatus.OK) {
                    var subscriptionResponse = subscriptionServices.findById(subscriptionId);
                    var moment = getDateSubscription(subscriptionResponse.getData().getType());
                    subsc.getData().add(new SubscribeModel(
                            moment[0],
                            moment[1],
                            true,
                            responseCustomer.get(),
                            subscriptionResponse.getData()
                    ));
                    subscribeServices.insertMany(subsc.getData());
                }
                return new MaiResponse.MaiSuccess<>(responseCustomer.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Customer not found.", HttpStatus.NOT_FOUND);
            }


        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
