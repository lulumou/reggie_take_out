package com.lumou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lumou.reggie.common.BaseContext;
import com.lumou.reggie.common.R;
import com.lumou.reggie.entity.ShoppingCart;
import com.lumou.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车数据:{}",shoppingCart);

        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,currentId);

        if(dishId!=null){
            //添加到购物车的是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId,dishId);

        }else {
            //添加到购物车的是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        //查询当前菜品或者套餐是否在购物车中
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id ?
        ShoppingCart cartServiceOne = shoppingCartService.getOne(lambdaQueryWrapper);

        if (cartServiceOne !=null){
            //如果已存在，就在原来的数据基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number+1);
            shoppingCartService.updateById(cartServiceOne);
        }else {
            //如果不存在，则添加到购物车，数量默认就是1
           shoppingCart.setNumber(1);
           shoppingCartService.save(shoppingCart);
           cartServiceOne = shoppingCart;

        }

        return R.success(cartServiceOne);
    }
}
