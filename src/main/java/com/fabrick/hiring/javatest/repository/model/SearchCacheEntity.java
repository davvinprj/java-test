package com.fabrick.hiring.javatest.repository.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCacheEntity{


    @EmbeddedId
    private SearchCacheEntityPk pk;
}
