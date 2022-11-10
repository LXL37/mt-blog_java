package com.meet.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lenovo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo {
    private List rows;

    private Long total;

}
