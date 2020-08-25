package com.bht.saigonparking.service.booking.service.main.impl;

import static com.bht.saigonparking.api.grpc.booking.BookingStatus.CREATED;
import static com.bht.saigonparking.api.grpc.booking.BookingStatus.FINISHED;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.api.grpc.user.MapToUsernameMapRequest;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.common.exception.BookingAlreadyFinishedException;
import com.bht.saigonparking.common.exception.BookingAlreadyRatedException;
import com.bht.saigonparking.common.exception.BookingNotYetAcceptedException;
import com.bht.saigonparking.common.exception.BookingNotYetFinishedException;
import com.bht.saigonparking.common.exception.BookingNotYetRatedException;
import com.bht.saigonparking.common.exception.PermissionDeniedException;
import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingHistoryEntity;
import com.bht.saigonparking.service.booking.entity.BookingRatingEntity;
import com.bht.saigonparking.service.booking.entity.BookingStatisticEntity;
import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;
import com.bht.saigonparking.service.booking.mapper.EnumMapper;
import com.bht.saigonparking.service.booking.repository.core.BookingHistoryRepository;
import com.bht.saigonparking.service.booking.repository.core.BookingRatingRepository;
import com.bht.saigonparking.service.booking.repository.core.BookingRepository;
import com.bht.saigonparking.service.booking.repository.core.BookingStatisticRepository;
import com.bht.saigonparking.service.booking.service.main.BookingService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookingServiceImpl implements BookingService {

    private final EnumMapper enumMapper;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    private final BookingRepository bookingRepository;
    private final BookingRatingRepository bookingRatingRepository;
    private final BookingHistoryRepository bookingHistoryRepository;
    private final BookingStatisticRepository bookingStatisticRepository;

    @Override
    public BookingEntity getOnGoingBookingOfCustomer(@NotNull Long customerId) {
        return bookingRepository.getFirstByCustomerIdAndIsFinished(customerId, Boolean.FALSE).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public BookingEntity getBookingByUuid(@NotEmpty String uuidString) {
        return bookingRepository.getBookingByUuid(UUID.fromString(uuidString)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public BookingEntity getBookingDetailByUuid(@NotEmpty String uuidString) {
        return bookingRepository.getBookingDetailByUuid(UUID.fromString(uuidString)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Pair<String, String> saveNewBooking(@NotNull BookingEntity bookingEntity) {
        BookingEntity newBookingEntity = bookingRepository.saveAndFlush(bookingEntity);
        return Pair.of(newBookingEntity.getUuid().toString(), newBookingEntity.getCreatedAt().toString());
    }

    @Override
    public void saveNewBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity, @NotNull String uuidString) {
        BookingEntity bookingEntity = getBookingByUuid(uuidString);
        saveNewBookingHistory(bookingHistoryEntity, bookingEntity);
    }

    private void saveNewBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity, @NotNull BookingEntity bookingEntity) {
        if (bookingEntity.getIsFinished().equals(Boolean.FALSE)) {
            bookingHistoryEntity.setBookingEntity(bookingEntity);
            bookingHistoryRepository.saveAndFlush(bookingHistoryEntity);
            return;
        }
        throw new BookingAlreadyFinishedException();
    }

    @Async
    @Override
    public void deleteBookingByUuid(@NotEmpty String uuidString) {
        bookingRepository.delete(getBookingByUuid(uuidString));
    }

    @Override
    public Pair<Long, Long> finishBooking(@NotEmpty String uuidString) {
        BookingEntity bookingEntity = getBookingByUuid(uuidString);

        if (bookingEntity.getBookingStatusEntity().equals(enumMapper.toBookingStatusEntity(CREATED))) {
            throw new BookingNotYetAcceptedException();
        }

        BookingHistoryEntity bookingHistoryEntity = BookingHistoryEntity.builder()
                .bookingStatusEntity(enumMapper.toBookingStatusEntity(FINISHED))
                .version(1L)
                .build();

        saveNewBookingHistory(bookingHistoryEntity, bookingEntity);
        return Pair.of(bookingEntity.getCustomerId(), bookingEntity.getParkingLotId());
    }

    @Override
    public Long countAllBooking() {
        return bookingRepository.countAllBooking();
    }

    @Override
    public Long countAllBooking(@NotNull BookingStatusEntity bookingStatusEntity) {
        return bookingRepository.countAllBooking(bookingStatusEntity);
    }

    @Override
    public Long countAllBookingOfCustomer(@NotNull Long customerId) {
        return bookingRepository.countAllBookingOfCustomer(customerId);
    }

    @Override
    public Long countAllBookingOfParkingLot(@NotNull Long parkingLotId) {
        return bookingRepository.countAllBookingOfParkingLot(parkingLotId);
    }

    @Override
    public Long countAllBookingOfParkingLot(@NotNull Long parkingLotId, @NotNull BookingStatusEntity bookingStatusEntity) {
        return bookingRepository.countAllBookingOfParkingLot(parkingLotId, bookingStatusEntity);
    }

    @Override
    public Long countAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId) {
        return bookingRepository.countAllOnGoingBookingOfParkingLot(parkingLotId);
    }

    @Override
    public List<BookingEntity> getAllBooking(@NotNull Integer nRow,
                                             @NotNull Integer pageNumber) {

        return bookingRepository.getAllBooking(nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllBooking(@NotNull BookingStatusEntity bookingStatusEntity,
                                             @NotNull Integer nRow,
                                             @NotNull Integer pageNumber) {

        return bookingRepository.getAllBooking(bookingStatusEntity, nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllBookingOfCustomer(@NotNull Long customerId,
                                                       @NotNull Integer nRow,
                                                       @NotNull Integer pageNumber) {

        return bookingRepository.getAllBookingOfCustomer(customerId, nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                         @NotNull Integer nRow,
                                                         @NotNull Integer pageNumber) {

        return bookingRepository.getAllBookingOfParkingLot(parkingLotId, nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                         @NotNull BookingStatusEntity bookingStatusEntity,
                                                         @NotNull Integer nRow,
                                                         @NotNull Integer pageNumber) {

        return bookingRepository.getAllBookingOfParkingLot(parkingLotId, bookingStatusEntity, nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId) {
        return bookingRepository.getAllOnGoingBookingOfParkingLot(parkingLotId);
    }

    @Override
    public Map<Long, Long> countAllBookingGroupByStatus() {
        return bookingRepository.countAllBookingGroupByStatus().stream().collect(Collectors
                .toMap(tuple -> enumMapper.toBookingStatusValue(tuple.get(0, Long.class)), tuple -> tuple.get(1, Long.class)));
    }

    @Override
    public Map<Long, Long> countAllBookingOfParkingLotGroupByStatus(@NotNull Long parkingLotId) {
        return bookingRepository.countAllBookingOfParkingLotGroupByStatus(parkingLotId).stream().collect(Collectors
                .toMap(tuple -> enumMapper.toBookingStatusValue(tuple.get(0, Long.class)), tuple -> tuple.get(1, Long.class)));
    }

    @Override
    public boolean checkCustomerHasOnGoingBooking(@NotNull Long customerId) {
        return bookingRepository.countAllUnfinishedBookingByCustomerId(customerId) != 0;
    }

    @Override
    public Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId) {
        return bookingRatingRepository.countAllRatingsOfParkingLot(parkingLotId);
    }

    @Override
    public Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                            @NotNull @Range(max = 5L) Integer rating) {

        if (rating.equals(0)) {
            return countAllRatingsOfParkingLot(parkingLotId);
        }
        return bookingRatingRepository.countAllRatingsOfParkingLot(parkingLotId, rating);
    }

    @Override
    public Map<Tuple, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                        boolean sortLastUpdatedAsc,
                                                        @NotNull @Max(20L) Integer nRow,
                                                        @NotNull Integer pageNumber) {

        List<Tuple> parkingLotRatingTupleList = bookingRatingRepository
                .getAllRatingsOfParkingLot(parkingLotId, sortLastUpdatedAsc, nRow, pageNumber);

        Map<Long, String> usernameMap = userServiceBlockingStub.mapToUsernameMap(MapToUsernameMapRequest.newBuilder()
                .addAllUserId(parkingLotRatingTupleList.stream()
                        .map(tuple -> tuple.get(2, Long.class)).collect(Collectors.toSet()))
                .build())
                .getUsernameMap();

        return parkingLotRatingTupleList.stream().collect(Collectors
                .toMap(parkingLotRatingTuple -> parkingLotRatingTuple,
                        parkingLotRatingTuple -> usernameMap.get(parkingLotRatingTuple.get(2, Long.class))));
    }

    @Override
    public Map<Tuple, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                        @NotNull @Range(max = 5L) Integer rating,
                                                        boolean sortLastUpdatedAsc,
                                                        @NotNull @Max(20L) Integer nRow,
                                                        @NotNull Integer pageNumber) {

        if (!rating.equals(0)) {
            List<Tuple> parkingLotRatingTupleList = bookingRatingRepository
                    .getAllRatingsOfParkingLot(parkingLotId, rating, sortLastUpdatedAsc, nRow, pageNumber);

            Map<Long, String> usernameMap = userServiceBlockingStub.mapToUsernameMap(MapToUsernameMapRequest.newBuilder()
                    .addAllUserId(parkingLotRatingTupleList.stream()
                            .map(tuple -> tuple.get(2, Long.class)).collect(Collectors.toSet()))
                    .build())
                    .getUsernameMap();

            return parkingLotRatingTupleList.stream().collect(Collectors
                    .toMap(parkingLotRatingTuple -> parkingLotRatingTuple,
                            parkingLotRatingTuple -> usernameMap.get(parkingLotRatingTuple.get(2, Long.class))));
        }

        return getAllRatingsOfParkingLot(parkingLotId, sortLastUpdatedAsc, nRow, pageNumber);
    }

    @Override
    public Map<Integer, Long> getParkingLotRatingCountGroupByRating(@NotNull Long parkingLotId) {
        return bookingRatingRepository.getParkingLotRatingCountGroupByRating(parkingLotId);
    }

    @Override
    public void createBookingRating(@NotNull Long customerId,
                                    @NotNull String bookingUuidString,
                                    @NotNull Integer rating,
                                    @NotEmpty String comment) {

        BookingEntity bookingEntity = getBookingByUuid(bookingUuidString);
        if (customerId.equals(bookingEntity.getCustomerId())) {
            if (Boolean.TRUE.equals(bookingEntity.getIsFinished())) {
                BookingRatingEntity currentBookingRating = bookingEntity.getBookingRatingEntity();

                if (currentBookingRating == null) {
                    BookingRatingEntity bookingRatingEntity = BookingRatingEntity.builder()
                            .bookingEntity(bookingEntity)
                            .rating(rating.shortValue())
                            .comment(comment)
                            .build();

                    bookingRatingRepository.saveAndFlush(bookingRatingEntity);
                    return;
                }
                throw new BookingAlreadyRatedException();
            }
            throw new BookingNotYetFinishedException();
        }
        throw new PermissionDeniedException();
    }

    @Override
    public void updateBookingRating(@NotNull Long customerId,
                                    @NotNull String bookingUuidString,
                                    @NotNull Integer rating,
                                    @NotEmpty String comment) {

        BookingEntity bookingEntity = getBookingByUuid(bookingUuidString);
        if (customerId.equals(bookingEntity.getCustomerId())) {
            BookingRatingEntity currentBookingRating = bookingEntity.getBookingRatingEntity();

            if (currentBookingRating != null) {
                currentBookingRating.setRating(rating.shortValue());
                currentBookingRating.setComment(comment);
                bookingRatingRepository.saveAndFlush(currentBookingRating);
                return;
            }
            throw new BookingNotYetRatedException();
        }
        throw new PermissionDeniedException();
    }

    @Override
    public void deleteBookingRating(@NotNull Long customerId, @NotEmpty String bookingUuidString) {

        BookingEntity bookingEntity = getBookingByUuid(bookingUuidString);
        if (customerId.equals(bookingEntity.getCustomerId())) {
            BookingRatingEntity currentBookingRating = bookingEntity.getBookingRatingEntity();

            if (currentBookingRating != null) {
                bookingRatingRepository.delete(currentBookingRating);
                return;
            }
            throw new BookingNotYetRatedException();
        }
        throw new PermissionDeniedException();
    }

    @Async
    @Override
    public void createOneOrManyParkingLotStatistic(@NotNull Set<Long> parkingLotIdSet) {
        parkingLotIdSet.forEach(this::createParkingLotStatistic);
    }

    @Async
    @Override
    public void deleteOneOrManyParkingLotStatistic(@NotNull Set<Long> parkingLotIdSet) {
        parkingLotIdSet.forEach(this::deleteParkingLotStatistic);
    }

    private void createParkingLotStatistic(@NotNull Long parkingLotId) {
        Optional<BookingStatisticEntity> currentStatistic = bookingStatisticRepository.getByParkingLotId(parkingLotId);
        if (!currentStatistic.isPresent()) {
            bookingStatisticRepository.saveAndFlush(BookingStatisticEntity.builder()
                    .parkingLotId(parkingLotId)
                    .nBooking(0L)
                    .nRating(0L)
                    .ratingAverage((double) 0)
                    .build());
        }
    }

    private void deleteParkingLotStatistic(@NotNull Long parkingLotId) {
        bookingStatisticRepository.getByParkingLotId(parkingLotId).ifPresent(bookingStatisticRepository::delete);
    }
}