package com.fabrick.hiring.javatest.repository;

import com.fabrick.hiring.javatest.repository.model.SearchCacheEntity;
import com.fabrick.hiring.javatest.repository.model.SearchCacheEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchCacheRepository extends JpaRepository<SearchCacheEntity, SearchCacheEntityPk> {

}
