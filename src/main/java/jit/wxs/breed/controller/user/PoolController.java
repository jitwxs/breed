package jit.wxs.breed.controller.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import jit.wxs.breed.controller.GlobalFunction;
import jit.wxs.breed.domain.Msg;
import jit.wxs.breed.domain.bo.PoolSelectWrapper;
import jit.wxs.breed.domain.entity.Pool;
import jit.wxs.breed.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 塘口Controller
 * @author jitwxs
 * @date 2018/4/25 15:53
 */
@RestController
@RequestMapping("/user/pool")
public class PoolController {
    @Autowired
    private PoolService poolService;
    @Autowired
    private GlobalFunction globalFunction;

    /**
     * 获取塘口列表
     * @author jitwxs
     * @since 2018/4/25 18:59
     */
    @GetMapping("/list")
    public Map listPool(@RequestParam(defaultValue = "10") Integer rows,@RequestParam(defaultValue = "1") Integer page, PoolSelectWrapper psw) {
        EntityWrapper<Pool> wrapper = globalFunction.getPoolWrapper(psw);
        wrapper.eq("user_id", globalFunction.getCurrentUserId());

        Page<Pool> selectPage = poolService.selectPage(new Page<>(page, rows), wrapper);

        Map<String,Object> map = new HashMap<>();
        map.put("total", selectPage.getTotal());
        map.put("rows", selectPage.getRecords());
        return map;
    }

    /**
     * 新增塘口
     * @author jitwxs
     * @since 2018/4/26 14:56
     */
    @PostMapping("")
    public Msg addPool(Pool pool) {
        pool.setUserId(globalFunction.getCurrentUserId());
        pool.setCreateDate(new Date());
        poolService.insert(pool);
        return Msg.ok();
    }

    /**
     * 更新塘口数据
     * @author jitwxs
     * @since 2018/5/17 11:18
     */
    @PutMapping("")
    public Msg updatePool(Pool pool) {
        poolService.updateById(pool);
        return Msg.ok();
    }

    /**
     * 获取在指定塘口
     * @author jitwxs
     * @since 2018/4/25 20:26
     */
    @GetMapping("/{id}")
    public Msg getById(@PathVariable String id) {
        Pool pool = poolService.selectById(id);
        return pool == null ? Msg.error("获取失败") : Msg.ok(null,pool);
    }

    /**
     * 根据id删除塘口，支持批量删除
     * @author jitwxs
     * @since 2018/4/25 20:29
     */
    @PostMapping ("/delete")
    public Msg deleteById(String[] ids) {
        for(String id : ids) {
            poolService.deleteById(id);
        }
        return Msg.ok();
    }
}
