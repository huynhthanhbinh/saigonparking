package com.bht.parkingmap.dbserver.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * Base Repository Custom Class
 *
 * Repository Custom Class is using for
 * extend original JPA Repository Interface
 * which means self-implement more methods
 * and/or complex, nested queries,...
 * that cannot achieve by usual JPA method
 *
 * How to setup ?
 * 1. interface XRepository will extends interface JpaRepository
 * 2. interface XRepository will extends interface XRepositoryCustom too
 *    --> in order to inherit all of methods inside interface XRCustom
 * 3. class XRepositoryCustomImpl will extends BaseRepositoryCustom
 * 4. class XRepositoryCustomImpl of course will implements XRepositoryCustom
 * 5. mark them with {@code @Repository} for Spring to init them as beans
 *
 * @author bht
 */
public abstract class BaseRepositoryCustom {

    /**
     *
     * Each EntityManager instance
     * is associated with a persistence context.
     *
     * Within the persistence context,
     * the entity instances and their lifecycle are managed.
     *
     * Persistence context defines a scope under
     * which particular entity instances are created, persisted, or removed.
     *
     * A persistence context is like a cache
     * which contains a set of persistent entities ,
     * So once the transaction is finished,
     * all persistent objects are detached
     * from the EntityManager's persistence context
     * and are no longer managed.
     */
    @PersistenceContext
    protected EntityManager entityManager;
}