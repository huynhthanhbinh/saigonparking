package com.bht.saigonparking.service.parkinglot.service.main.impl;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.PARKING_LOT_ROUTING_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.api.grpc.parkinglot.DeleteParkingLotNotification;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotEmployeeInfo;
import com.bht.saigonparking.api.grpc.user.MapToUsernameListRequest;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEmployeeEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotLimitEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotRatingEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotTypeEntity;
import com.bht.saigonparking.service.parkinglot.repository.core.ParkingLotInformationRepository;
import com.bht.saigonparking.service.parkinglot.repository.core.ParkingLotLimitRepository;
import com.bht.saigonparking.service.parkinglot.repository.core.ParkingLotRatingRepository;
import com.bht.saigonparking.service.parkinglot.repository.core.ParkingLotRepository;
import com.bht.saigonparking.service.parkinglot.service.main.ParkingLotService;

import lombok.AllArgsConstructor;

/**
 *
 * this class implements all services relevant to ParkingLot
 *
 * for clean code purpose,
 * using {@code @AllArgsConstructor} for Service class
 * it will {@code @Autowired} all attributes declared inside
 * hide {@code @Autowired} as much as possible in code
 * remember to mark all attributes as {@code private final}
 *
 * @author bht
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ParkingLotServiceImpl implements ParkingLotService {

    private final RabbitTemplate rabbitTemplate;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotLimitRepository parkingLotLimitRepository;
    private final ParkingLotRatingRepository parkingLotRatingRepository;
    private final ParkingLotInformationRepository parkingLotInformationRepository;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public Long countAll(@NotEmpty String keyword, boolean isAvailableOnly) {

        if (keyword.isEmpty()) {
            if (isAvailableOnly) { /* count all available */
                return parkingLotRepository.countAll(true);
            } else { /* count all */
                return parkingLotRepository.countAll();
            }

        } else {
            if (isAvailableOnly) { /* count all with keyword, available */
                return parkingLotRepository.countAll(keyword, true);
            } else { /* count all with keyword */
                return parkingLotRepository.countAll(keyword);
            }
        }
    }

    @Override
    public Long countAll(@NotEmpty String keyword, boolean isAvailableOnly, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        if (keyword.isEmpty()) {
            if (isAvailableOnly) { /* count all by type, available */
                return parkingLotRepository.countAll(parkingLotTypeEntity, true);
            } else { /* count all by type */
                return parkingLotRepository.countAll(parkingLotTypeEntity);
            }

        } else {
            if (isAvailableOnly) { /* count all by type, with keyword, available */
                return parkingLotRepository.countAll(keyword, parkingLotTypeEntity, true);
            } else { /* count all by type, with keyword */
                return parkingLotRepository.countAll(keyword, parkingLotTypeEntity);
            }
        }
    }

    @Override
    public Long countAllHasRatings() {
        return parkingLotInformationRepository.countAllHasRatings();
    }

    @Override
    public Long countAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                   @NotNull @Range(max = 5L) Integer upperBound) {

        if (lowerBound.equals(upperBound) && lowerBound.equals(0)) {
            return countAllHasRatings();
        }
        if (lowerBound.equals(upperBound) || lowerBound.compareTo(upperBound) < 0) {
            return parkingLotInformationRepository.countAllHasRatings(lowerBound, upperBound);
        }
        return 0L;
    }

    @Override
    public Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId) {
        return parkingLotRatingRepository.countAllRatingsOfParkingLot(parkingLotId);
    }

    @Override
    public Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                            @NotNull @Range(max = 5L) Integer rating) {

        if (rating.equals(0)) {
            return countAllRatingsOfParkingLot(parkingLotId);
        }
        return parkingLotRatingRepository.countAllRatingsOfParkingLot(parkingLotId, rating);
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull Set<Long> parkingLotIdSet) {
        return parkingLotIdSet.isEmpty()
                ? Collections.emptyList()
                : parkingLotRepository.getAll(parkingLotIdSet);
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                         @NotNull Integer pageNumber,
                                         @NotEmpty String keyword,
                                         boolean isAvailableOnly) {

        if (keyword.isEmpty()) {
            if (isAvailableOnly) { /* get all available */
                return parkingLotRepository.getAll(nRow, pageNumber, true);
            } else { /* get all */
                return parkingLotRepository.getAll(nRow, pageNumber);
            }

        } else {
            if (isAvailableOnly) { /* get all with keyword, available */
                return parkingLotRepository.getAll(nRow, pageNumber, keyword, true);
            } else { /* get all with keyword */
                return parkingLotRepository.getAll(nRow, pageNumber, keyword);
            }
        }
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                         @NotNull Integer pageNumber,
                                         @NotEmpty String keyword,
                                         boolean isAvailableOnly,
                                         @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        if (keyword.isEmpty()) {
            if (isAvailableOnly) { /* get all by type, available */
                return parkingLotRepository.getAll(nRow, pageNumber, parkingLotTypeEntity, true);
            } else { /* get all by type */
                return parkingLotRepository.getAll(nRow, pageNumber, parkingLotTypeEntity);
            }

        } else {
            if (isAvailableOnly) { /* get all by type, with keyword, available */
                return parkingLotRepository.getAll(nRow, pageNumber, keyword, parkingLotTypeEntity, true);
            } else { /* get all by type, with keyword */
                return parkingLotRepository.getAll(nRow, pageNumber, keyword, parkingLotTypeEntity);
            }
        }
    }

    @Override
    public List<ParkingLotInformationEntity> getAllHasRatings(boolean sortRatingAsc,
                                                              @NotNull @Max(20L) Integer nRow,
                                                              @NotNull Integer pageNumber) {

        return parkingLotInformationRepository.getAllHasRatings(sortRatingAsc, nRow, pageNumber);
    }

    @Override
    public List<ParkingLotInformationEntity> getAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                                              @NotNull @Range(max = 5L) Integer upperBound,
                                                              boolean sortRatingAsc,
                                                              @NotNull @Max(20L) Integer nRow,
                                                              @NotNull Integer pageNumber) {

        if (lowerBound.equals(upperBound) && lowerBound.equals(0)) {
            return getAllHasRatings(sortRatingAsc, nRow, pageNumber);
        }
        if (lowerBound.equals(upperBound) || lowerBound.compareTo(upperBound) < 0) {
            return parkingLotInformationRepository.getAllHasRatings(lowerBound, upperBound, sortRatingAsc, nRow, pageNumber);
        }
        return Collections.emptyList();
    }

    @Override
    public Map<ParkingLotRatingEntity, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                                         boolean sortLastUpdatedAsc,
                                                                         @NotNull @Max(20L) Integer nRow,
                                                                         @NotNull Integer pageNumber) {
        List<ParkingLotRatingEntity> parkingLotRatingEntityList = parkingLotRatingRepository
                .getAllRatingsOfParkingLot(parkingLotId, sortLastUpdatedAsc, nRow, pageNumber);

        Map<Long, String> usernameMap = userServiceBlockingStub.mapToUsernameList(MapToUsernameListRequest.newBuilder()
                .addAllUserId(parkingLotRatingEntityList.stream()
                        .map(ParkingLotRatingEntity::getCustomerId).collect(Collectors.toSet()))
                .build())
                .getUsernameMap();

        return parkingLotRatingEntityList.stream().collect(Collectors
                .toMap(parkingLotRatingEntity -> parkingLotRatingEntity,
                        parkingLotRatingEntity -> usernameMap.get(parkingLotRatingEntity.getCustomerId())));
    }

    @Override
    public Map<ParkingLotRatingEntity, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                                         @NotNull @Range(max = 5L) Integer rating,
                                                                         boolean sortLastUpdatedAsc,
                                                                         @NotNull @Max(20L) Integer nRow,
                                                                         @NotNull Integer pageNumber) {
        if (!rating.equals(0)) {
            List<ParkingLotRatingEntity> parkingLotRatingEntityList = parkingLotRatingRepository
                    .getAllRatingsOfParkingLot(parkingLotId, rating, sortLastUpdatedAsc, nRow, pageNumber);

            Map<Long, String> usernameMap = userServiceBlockingStub.mapToUsernameList(MapToUsernameListRequest.newBuilder()
                    .addAllUserId(parkingLotRatingEntityList.stream()
                            .map(ParkingLotRatingEntity::getCustomerId).collect(Collectors.toSet()))
                    .build())
                    .getUsernameMap();

            return parkingLotRatingEntityList.stream().collect(Collectors
                    .toMap(parkingLotRatingEntity -> parkingLotRatingEntity,
                            parkingLotRatingEntity -> usernameMap.get(parkingLotRatingEntity.getCustomerId())));
        }
        return getAllRatingsOfParkingLot(parkingLotId, sortLastUpdatedAsc, nRow, pageNumber);
    }

    @Override
    public Map<Integer, Long> getParkingLotRatingCountGroupByRating(@NotNull Long parkingLotId) {
        return parkingLotRatingRepository.getParkingLotRatingCountGroupByRating(parkingLotId);
    }

    @Override
    public ParkingLotEntity getParkingLotById(@NotNull Long id) {
        return parkingLotRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ParkingLotLimitEntity getParkingLotLimitById(@NotNull Long id) {
        return parkingLotLimitRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Boolean checkAvailability(@NotNull Long parkingLotId) {
        return parkingLotRepository.checkAvailability(parkingLotId);
    }

    @Override
    public List<Long> checkUnavailability(@NotEmpty List<Long> parkingLotIdList) {
        return parkingLotRepository.checkUnavailability(parkingLotIdList);
    }

    @Override
    public List<Tuple> getTopParkingLotInRegionOrderByDistanceWithoutName(@NotNull Double lat,
                                                                          @NotNull Double lng,
                                                                          @NotNull Integer radius,
                                                                          @NotNull Integer nResult) {
        return parkingLotRepository.getTopParkingLotInRegionOrderByDistanceWithoutName(lat, lng, radius, nResult);
    }

    @Override
    public List<Tuple> getTopParkingLotInRegionOrderByDistanceWithName(@NotNull Double lat,
                                                                       @NotNull Double lng,
                                                                       @NotNull Integer radius,
                                                                       @NotNull Integer nResult) {
        return parkingLotRepository.getTopParkingLotInRegionOrderByDistanceWithName(lat, lng, radius, nResult);
    }

    @Override
    public void deleteParkingLotById(@NotNull Long id) {
        ParkingLotEntity parkingLotEntity = getParkingLotById(id);
        List<Long> employeeIdList = parkingLotEntity.getParkingLotEmployeeEntitySet().stream()
                .map(ParkingLotEmployeeEntity::getUserId)
                .collect(Collectors.toList());

        parkingLotRepository.delete(parkingLotEntity);

        rabbitTemplate.convertAndSend(PARKING_LOT_ROUTING_KEY, DeleteParkingLotNotification.newBuilder()
                .addAllInfo(Collections
                        .singletonList(ParkingLotEmployeeInfo.newBuilder()
                                .setParkingLotId(id)
                                .addAllEmployeeId(employeeIdList)
                                .build()))
                .build());
    }

    @Override
    public void deleteMultiParkingLotById(@NotNull Set<Long> parkingLotIdSet) {
        if (!parkingLotIdSet.isEmpty()) {
            List<ParkingLotEntity> parkingLotEntityList = getAll(parkingLotIdSet);
            if (!parkingLotEntityList.isEmpty()) {

                List<ParkingLotEmployeeInfo> parkingLotEmployeeInfoList = new ArrayList<>();

                parkingLotEntityList.forEach(parkingLotEntity -> parkingLotEmployeeInfoList
                        .add(ParkingLotEmployeeInfo.newBuilder()
                                .setParkingLotId(parkingLotEntity.getId())
                                .addAllEmployeeId(parkingLotEntity.getParkingLotEmployeeEntitySet().stream()
                                        .map(ParkingLotEmployeeEntity::getUserId)
                                        .collect(Collectors.toList()))
                                .build()));

                parkingLotRepository.deleteAll(parkingLotEntityList);

                rabbitTemplate.convertAndSend(PARKING_LOT_ROUTING_KEY, DeleteParkingLotNotification.newBuilder()
                        .addAllInfo(parkingLotEmployeeInfoList)
                        .build());
            }
        }
    }
}