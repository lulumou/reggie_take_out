package com.lumou.reggie.dto;


import com.lumou.reggie.entity.Setmeal;
import com.lumou.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
