<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="addFinance" class="hide">
	<form action="#" class="form-horizontal" onsubmit="return false;">
		<input type="hidden" name="id" id="id" value=""/>
		<div class="control-group">
			<label class="control-label" for="inputWarning">分类名称：</label>
			<div class="controls">
				<input type="text" name="name" id="name" valid-options="{'inputName':'分类名称','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});">
				<span class="help-inline"></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="inputError">分类编号：</label>
			<div class="controls">
				<input type="text" name="code" id="code" valid-options="{'type':'number','inputName':'分类编号','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});">
				<span class="help-inline"></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="inputSuccess">初始投入：</label>
			<div class="controls">
				<input type="text" name="firstAmount" id="firstAmount" valid-options="{'type':'double','inputName':'初始投入','required':true}" onfocus="LaValidForm.focusValid({'obj':this})" onblur="LaValidForm.blurValid({'obj':this});">
				<span class="help-inline"></span>
			</div>
		</div>

		<button type="submit" class="btn btn-red" onclick="saveFenLei();">保 存</button>
	</form>
</div>
<script type="text/javascript">
	function doFenLei(id){
		var html = $("#addFinance").clone();
		$(html).find("form").attr("id", "autoform");
		if(id != undefined){
			$(html).find("#id").val(id);
			$.ajax({
				url : "?method=getFenLeiById",
				data : {"id" : id},
				dataType : "json",
				type : "post",
				success : function(result){
					var data = result.data;
					$(html).find("#name").val(data.name);
					$(html).find("#code").val(data.code);
					$(html).find("#firstAmount").val(data.firstAmount);
					LaModal.initModal({title:"修改理财分类",width:560,cont:html.show()});
				}
			});
		} else {
			LaModal.initModal({title:"添加理财分类",width:560,cont:html.show()});
		}
		
	}

	function saveFenLei(){
		var result = LaValidForm.valid("#autoform");
		
		if(result){
			$.ajax({
				url : "?method=doFenLei",
				dataType : "JSON",
				type : "POST",
				data : $("#autoform").serialize(),
				success : function(result){
					if(result.suc){
						window.location.href = "?method=fenlei";
					} else {
						alert(result.info);
					}
				}
			});
		}
	}
</script>
