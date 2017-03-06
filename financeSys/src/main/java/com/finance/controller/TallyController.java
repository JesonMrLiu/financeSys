package com.finance.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.finance.bean.ResultBean;
import com.finance.bean.enums.MonthEnum;
import com.finance.entity.Tally;
import com.finance.service.TallyService;
import com.finance.util.DateUtils;
import com.finance.util.PropertiesUtils;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
@RequestMapping("tallyController.do")
public class TallyController extends BaseController {

	@Autowired
	private TallyService service;
	
	@RequestMapping(params = "method=tally")
	public String tally(){
		init();
		try {
			String _page_ = param("page");
			if(StringUtils.isNotEmpty(_page_)){
				this.page = Integer.valueOf(_page_);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", getCurrentUser().getId());
			int year = intParam("y");
			int month = intParam("m");
			if(year > 0 && month > 0){
				String ym = year + "-" + (month < 10 ? ("0" + month) : month);
				param.put("month", ym);
			}
			int type = intParam("t");
			if(type > 0){
				param.put("type", type);
			}
			int payType = intParam("pt");
			if(payType > 0){
				param.put("payType", payType);
			}
			
			List<Tally> list = service.getTallys(param, new PageBounds(page, size, Order.formString("tallyDate.DESC")));
			request.setAttribute("dataList", list);
			initPage(list);
			
			//当期年份，月份
			Calendar now = Calendar.getInstance();
			int curYear = now.get(Calendar.YEAR);
			int curMonth = now.get(Calendar.MONTH) + 1;
			if(year <= 0) request.setAttribute("curYear", curYear);
			if(month <= 0) request.setAttribute("curMonth", curMonth);
			//获取条件下拉框的年份
			String count = PropertiesUtils.getContextProperty("tally.show.year");
			List<String> years = DateUtils.arrayBeforeYearOfCount(Integer.valueOf(count));
			request.setAttribute("years", years);
			//月份集合
			request.setAttribute("months", MonthEnum.getMonths());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tallys/tally";
	}
	
	/**
	 * 导入多个Excel文件数据
	 *
	 * @author Liugang
	 * @time 2017-3-3
	 * @param files
	 * @return
	 */
	@RequestMapping(params = "method=importDatas")
	public String importDatas(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
	    InputStream is = null;
	    int month = 0;//导入之后，跳转到的月份
	    int year = 0;//导入之后，需要跳转的年份
	    try {
	      if (file == null) {
	        return null;
	      }
	      List<Tally> datas = new ArrayList<Tally>();//保存从excel取出来的待持久化到数据库的数据集合
    	  is = file.getInputStream();
    	  POIFSFileSystem fs = new POIFSFileSystem(is);
    	  HSSFWorkbook book = new HSSFWorkbook(fs);
    	  HSSFSheet sheet = book.getSheetAt(0);
    	  int lastRow = sheet.getLastRowNum();
    	  int startRow = 2;
    	  DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    	  for (; startRow < lastRow + 1; startRow++) {
    		  HSSFRow row = sheet.getRow(startRow);
    		  Tally tally = new Tally();
    		  tally.setUserId(getCurrentUser().getId());
    		  tally.setTitle(row.getCell(0).getStringCellValue());
    		  //账目日期
    		  String tallyDateStr = row.getCell(1).getStringCellValue();
    		  Date tallyDate = fmt.parse(tallyDateStr);
    		  tally.setTallyDate(tallyDate);
    		  //获得正在导入的年份、月份
    		  if(year == 0){
    			  String[] arr = tallyDateStr.split("-");
    			  year = Integer.parseInt(arr[0]);
    			  month = Integer.parseInt(arr[1]);
    		  }
    		  
    		  //支出类型。1-支出；2-收入
    		  String payType = row.getCell(2).getStringCellValue();
    		  tally.setPayType("支出".equals(payType) ? 1 : 2);
    		  //收支金额
    		  String amountStr = row.getCell(3).getStringCellValue();
    		  amountStr = amountStr.replace("-", "").replace("+", "");
    		  BigDecimal amount = new BigDecimal(amountStr);
    		  tally.setAmount(amount);
    		  //总金额
    		  String totalAmountStr = row.getCell(4).getStringCellValue();
    		  totalAmountStr = totalAmountStr.replace("-", "").replace("+", "");
    		  BigDecimal totalAmount = new BigDecimal(totalAmountStr);
    		  tally.setTotalAmount(totalAmount);
    		  //账目类型，账目类型名称
    		  String typeStr = row.getCell(5).getStringCellValue();
    		  int type = "消费等".equals(typeStr) ? 1 : 2;
    		  tally.setType(type);
    		  tally.setTypeName(typeStr);
    		  //付款方
    		  String payer = row.getCell(6).getStringCellValue();
    		  tally.setPayer(payer);
    		  //收款人
    		  String payee = row.getCell(7).getStringCellValue();
    		  tally.setPayee(payee);
    		  //备注
    		  String remark = row.getCell(8).getStringCellValue();
    		  tally.setRemark(remark);
    		  //创建日期，创建时间
    		  tally.setCreateDate(tallyDate);
    		  datas.add(tally);
    	  }
    	  if (!datas.isEmpty()) {
    		  Map<String, List<Tally>> moreTally = new HashMap<String, List<Tally>>();
    		  moreTally.put("datas", datas);
    		  service.addMoreTallys(moreTally);
    	  }
	    } catch (Exception e) {
	      e.printStackTrace();
	      this.res = new ResultBean("抱歉，导入Excel数据失败！", false, "");
	    } finally {
	      if (is != null) {
	        is.close();
	      }
	    }
	    return "redirect:tallyController.do?method=tally&y="+year+"&m="+month;
	}
	
	/**
	 * 导出Excel
	 *
	 * @author Liugang
	 * @time 2017-3-3
	 * @return
	 */
	public String exportXls(){
		
		return null;
	}
	
	@RequestMapping(params = "method=doTally")
	public String doTally(@ModelAttribute Tally tally){
		try {
			int id = tally.getId();
			if(id > 0){////////修改
				Tally temp = service.getTallyById(tally);
				BigDecimal totalAmount = temp.getPayType() == 1 ? temp.getTotalAmount().subtract(temp.getAmount()) : temp.getTotalAmount().add(temp.getAmount());
				tally.setTotalAmount(totalAmount);
				service.updateTallyById(tally);
			} else {
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:tallyController.do?method=tally";
	}
	
}
