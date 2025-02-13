package com.standalone.standalone;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link StandaloneAggregate} entities.
 *
 * <p>
 * This interface extends Spring Data JPA's {@code JpaRepository} to provide
 * CRUD operations and query methods for {@code StandaloneAggregate} instances.
 * It leverages Spring Data's method query derivation and provides basic persistence
 * functionality out-of-the-box.
 * </p>
 *
 * @author Torsoto
 * @version 1.0
 */
public interface StandaloneAggregateRepository extends JpaRepository<StandaloneAggregate, String> {
}
