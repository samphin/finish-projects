package com.ryit.users.controller;

import com.ryit.commons.base.controller.IBaseController;
import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.constants.BaseUrlConstants;
import com.ryit.commons.entity.dto.BusiDeliveryAddressDto;
import com.ryit.commons.entity.vo.BusiDeliveryAddressVo;
import com.ryit.users.service.IBusiDeliveryAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 收货地址管理
 *
 * @author samphin
 * @since 2019-10-28 11:30:14
 */
@Api(value = "BusiDeliveryAddressController", tags = "收货地址信息接口")
@RestController
@RequestMapping(value = BaseUrlConstants.BASE_API_PREFIX + "/delivery_address")
public class BusiDeliveryAddressController implements IBaseController<Long, BusiDeliveryAddressDto> {

    @Autowired
    private IBusiDeliveryAddressService busiDeliveryAddressService;

    /**
     * 新增收货地址
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "新增收货地址", httpMethod = "POST")
    @PostMapping
    public ResponseData save(@Validated(BusiDeliveryAddressDto.Save.class) @RequestBody BusiDeliveryAddressDto dto, HttpServletRequest request) {
        boolean bl = busiDeliveryAddressService.insertSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 修改收货地址（包含设置默认地址、给地址打标签等）
     *
     * @param dto
     * @param request
     * @return
     */
    @Override
    @ApiOperation(value = "修改收货地址", httpMethod = "PUT")
    @PutMapping
    public ResponseData update(@Validated(BusiDeliveryAddressDto.Update.class) @RequestBody BusiDeliveryAddressDto dto, HttpServletRequest request) {
        boolean bl = busiDeliveryAddressService.updateByIdSelective(dto, request);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 删除收货地址
     *
     * @return
     */
    @Override
    @ApiOperation(value = "删除收货地址", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable("id") Long id) {
        boolean bl = busiDeliveryAddressService.deleteById(id);
        if (bl) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 查询我的收货地址列表
     *
     * @return
     */
    @ApiOperation(value = "查询收货地址列表", httpMethod = "GET")
    @GetMapping
    public ResponseData queryMyAddress(HttpServletRequest request) {
        List<BusiDeliveryAddressVo> voList = busiDeliveryAddressService.queryMyAddress(request);
        return ResponseData.success().setData(voList);
    }

    /**
     * 查询收货地址详情
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "查询收货地址详情", httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseData queryOne(@PathVariable("id") Long id) {
        BusiDeliveryAddressVo vo = busiDeliveryAddressService.queryById(id);
        return ResponseData.success().setData(vo);
    }
}
