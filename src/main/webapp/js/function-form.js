var basePath='/quiz/';
var timeoutSetting = 70000;
var jsonData;
var currentPageIndex = 0;

$(document).ready(function(){
	
	
	loadJsonData(formid);
	
	jQuery.ajaxSetup({
		beforeSend: function(){
			displayLoadIndc();
		},
		complete: function(){
			hideLoadIndc();
		},
		success: function(){
		}
	});

	
});

function loadJsonData(formid){
	$.ajax({
        type: "POST",
        url: basePath+"loadSandbox",
        data: formid,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        timeout: timeoutSetting,
        success: function(data){
        	jsonData = data;
        	console.log('loadJsonData=>'+JSON.stringify(jsonData))
        	renderJsonData(jsonData, currentPageIndex);
        	
        },
        failure: function(errMsg) {
            alert(errMsg);
        },
        error: function (xhr, textStatus, errorThrown){
        	//openErrorDialog(plsCon);
        }
  });
}

function renderJsonData(data, pageIndex){
	renderForm(data, pageIndex);
	//renderNavQuestion(data.pages[pageIndex]);
	renderPagination(data, currentPageIndex);
}

function renderForm(data, pageIndex){
	
	$('#questions').children().remove();
	var title = data.setup.title;
	$('#title').text(title);
	
	var count = 1;
	var items = data.pages[pageIndex].items;
	for (var i = 0; items != null && i < items.length; i++) {
		var item = items[i];
		item.edit = false;
		var msg = '';
		var editbar = new EJS({url: basePath+'template/question-editbar.ejs'}).render(item);
		if (item.type == 'radio') {
			item.count = count++;
			msg += new EJS({url: basePath+'template/question_multiple.ejs'}).render(item);
		} else if (item.type == 'checkbox') {
			item.count = count++;
			msg += new EJS({url: basePath+'template/question_multiple.ejs'}).render(item);
		} else if (item.type == 'truefalse'){
			item.count = count++;
			//msg += new EJS({url: basePath+'template/question_truefalse.ejs'}).render(item);
			msg += new EJS({url: basePath+'template/question_multiple.ejs'}).render(item);
		} else if (item.type == 'textbox'){
			item.count = count++;
			msg += new EJS({url: basePath+'template/question_textbox.ejs'}).render(item);
		} else if (item.type == 'textarea'){
			item.count = count++;
			msg += new EJS({url: basePath+'template/question_textarea.ejs'}).render(item);
		} else if (item.type == 'imageOnly'){
			item.count = null;
			msg += new EJS({url: basePath+'template/imageOnly.ejs'}).render(item);
		} else if (item.type == 'textOnly'){
			item.count = null;
			msg += new EJS({url: basePath+'template/textOnly.ejs'}).render(item);
		} 
		
		
		$('#questions').append(msg);
		$('li[itemsn='+item.itemsn+']').append(editbar)
	}
}

function renderNavQuestion(page){
	var items = page.items;
	if (items){
		var msg = '';
		msg += new EJS({url: basePath+'template/nav_questions.ejs'}).render(page);
		$('#nav-questions').html(msg);
	} else {
		$('#nav-questions').html('');
	}
}

function renderPagination(data, currentPageIndex) {
	
	var obj = new Object();
	obj = data;
	obj.currentPageIndex = currentPageIndex;
	obj.edit = false;
	var msg = '';
	msg += new EJS({url: basePath+'template/page.ejs'}).render(obj);
	$('#pages').html(msg);
}

function hideLoadIndc() {
	setTimeout(function(){
		document.getElementById('busy_indicator').style.visibility = 'hidden';
	}, 200 );
	
};
function displayLoadIndc() {
	document.getElementById('busy_indicator').style.visibility = 'visible';
};

