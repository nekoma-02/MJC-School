package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    private static final int DEFAULT_LIMIT = 10;
    @PositiveOrZero
    private int limit;
    @PositiveOrZero
    private int offset;

    public int getLimit() {
        return limit == 0 ? DEFAULT_LIMIT : limit;
    }
}
