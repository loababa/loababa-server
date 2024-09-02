package com.loababa.api.consulting.persistence.repository;

import com.loababa.api.consulting.constant.ConsultingStatus;
import com.loababa.api.consulting.persistence.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(value = """
            SELECT COUNT(*) > 0
            FROM consulting_reservations reservation 
            JOIN consulting_reservation_datetimes datetime
              ON reservation.id = datetime.reservation_id
            WHERE reservation.lossam_id = :lossamId AND
                  dateTime.start_date_time = :slotStartTime AND
                  dateTime.end_date_time = :slotEndTime AND
                  dateTime.is_confirmed = true AND
                  reservation.is_deleted = false
            LIMIT 1
            """, nativeQuery = true)
    boolean existsReservation(long lossamId, LocalDateTime slotStartTime, LocalDateTime slotEndTime);

    List<ReservationEntity> findAllByLossamIdAndConsultingStatus(long lossamId, ConsultingStatus consultingStatus);

    List<ReservationEntity> findAllByMokokoIdAndConsultingStatus(long mokokoId, ConsultingStatus consultingStatus);
}
