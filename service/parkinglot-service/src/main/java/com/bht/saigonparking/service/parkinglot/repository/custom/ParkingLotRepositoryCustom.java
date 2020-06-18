package com.bht.saigonparking.service.parkinglot.repository.custom;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotTypeEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotRepositoryCustom {

    Long countAll();

    Long countAll(@NotNull Boolean isAvailable);

    Long countAll(@NotEmpty String keyword);

    Long countAll(@NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    Long countAll(@NotEmpty String keyword, @NotNull Boolean isAvailable);

    Long countAll(@NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable);

    Long countAll(@NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    Long countAll(@NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotNull Boolean isAvailable);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword,
                                  @NotNull Boolean isAvailable);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotNull ParkingLotTypeEntity parkingLotTypeEntity,
                                  @NotNull Boolean isAvailable);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword,
                                  @NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword,
                                  @NotNull ParkingLotTypeEntity parkingLotTypeEntity,
                                  @NotNull Boolean isAvailable);
}