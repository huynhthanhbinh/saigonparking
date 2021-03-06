package com.bht.saigonparking.service.parkinglot.service.main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotLimitEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotTypeEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    Long getParkingLotIdByParkingLotEmployeeId(@NotNull Long parkingLotEmployeeId);

    Long countAll(@NotEmpty String keyword, boolean isAvailableOnly);

    Long countAll(@NotEmpty String keyword, boolean isAvailableOnly,
                  @NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    List<ParkingLotEntity> getAll(@NotNull Set<Long> parkingLotIdSet);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword,
                                  boolean isAvailableOnly);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword,
                                  boolean isAvailableOnly,
                                  @NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    ParkingLotEntity getParkingLotById(@NotNull Long id);

    ParkingLotEntity getParkingLotByEmployeeId(@NotNull Long parkingLotEmployeeId);

    ParkingLotLimitEntity getParkingLotLimitById(@NotNull Long id);

    Boolean checkAvailability(@NotNull Long parkingLotId);

    List<Long> checkUnavailability(@NotEmpty List<Long> parkingLotIdList);

    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithoutName(@NotNull Double lat,
                                                                   @NotNull Double lng,
                                                                   @NotNull Integer radius,
                                                                   @NotNull Integer nResult);

    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithName(@NotNull Double lat,
                                                                @NotNull Double lng,
                                                                @NotNull Integer radius,
                                                                @NotNull Integer nResult);

    void deleteParkingLotById(@NotNull Long id);

    void deleteMultiParkingLotById(@NotNull Set<Long> parkingLotIdSet);

    void updateAvailability(@NotNull Short newAvailability, @NotNull Long parkingLotId);

    String getParkingLotNameByParkingLotId(@NotNull Long parkingLotId);

    Map<Long, String> mapToParkingLotNameMap(@NotNull Set<Long> parkingLotIdSet);

    Map<Long, Long> countAllParkingLotGroupByType();

    Long createNewParkingLot(@NotNull ParkingLotEntity parkingLotEntity,
                             @NotNull ParkingLotLimitEntity parkingLotLimitEntity,
                             @NotNull ParkingLotInformationEntity parkingLotInformationEntity);

    boolean checkEmployeeAlreadyManageParkingLot(@NotNull Long employeeId);

    List<Long> getEmployeeManageParkingLotIdList(@NotNull Long parkingLotId);

    void addEmployeeOfParkingLot(@NotNull Long employeeId, @NotNull Long parkingLotId);

    void removeEmployeeOfParkingLot(@NotNull Long employeeId, @NotNull Long parkingLotId, boolean deleteEmployee);
}