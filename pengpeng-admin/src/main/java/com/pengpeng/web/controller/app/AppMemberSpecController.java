package com.pengpeng.web.controller.app;

import java.util.List;

import com.pengpeng.v4.pojo.member.PpMemberSpec;
import com.pengpeng.v4.service.member.IPpMemberSpecService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pengpeng.common.annotation.Log;
import com.pengpeng.common.enums.BusinessType;
import com.pengpeng.common.core.controller.BaseController;
import com.pengpeng.common.core.domain.AjaxResult;
import com.pengpeng.common.utils.poi.ExcelUtil;
import com.pengpeng.common.core.page.TableDataInfo;

/**
 * 会员规格Controller
 * 
 * @author pengpeng
 * @date 2020-01-02
 */
@Controller
@RequestMapping("/app/spec")
public class AppMemberSpecController extends BaseController
{
    private String prefix = "app/spec";

    @Autowired
    private IPpMemberSpecService ppMemberSpecService;

    @RequiresPermissions("system:spec:view")
    @GetMapping()
    public String spec()
    {
        return prefix + "/spec";
    }

    /**
     * 查询会员规格列表
     */
    @RequiresPermissions("system:spec:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PpMemberSpec ppMemberSpec)
    {
        startPage();
        List<PpMemberSpec> list = ppMemberSpecService.selectPpMemberSpecList(ppMemberSpec);
        return getDataTable(list);
    }

    /**
     * 导出会员规格列表
     */
    @RequiresPermissions("system:spec:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PpMemberSpec ppMemberSpec)
    {
        List<PpMemberSpec> list = ppMemberSpecService.selectPpMemberSpecList(ppMemberSpec);
        ExcelUtil<PpMemberSpec> util = new ExcelUtil<PpMemberSpec>(PpMemberSpec.class);
        return util.exportExcel(list, "spec");
    }

    /**
     * 新增会员规格
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存会员规格
     */
    @RequiresPermissions("system:spec:add")
    @Log(title = "会员规格", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PpMemberSpec ppMemberSpec)
    {
        return toAjax(ppMemberSpecService.insertPpMemberSpec(ppMemberSpec));
    }

    /**
     * 修改会员规格
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        PpMemberSpec ppMemberSpec = ppMemberSpecService.selectPpMemberSpecById(id);
        mmap.put("ppMemberSpec", ppMemberSpec);
        return prefix + "/edit";
    }

    /**
     * 修改保存会员规格
     */
    @RequiresPermissions("system:spec:edit")
    @Log(title = "会员规格", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PpMemberSpec ppMemberSpec)
    {
        return toAjax(ppMemberSpecService.updatePpMemberSpec(ppMemberSpec));
    }

    /**
     * 删除会员规格
     */
    @RequiresPermissions("system:spec:remove")
    @Log(title = "会员规格", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(ppMemberSpecService.deletePpMemberSpecByIds(ids));
    }
}
