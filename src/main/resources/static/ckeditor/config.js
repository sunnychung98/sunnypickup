/**
 * @license Copyright (c) 2003-2020, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {

	//메뉴버튼 
	config.toolbarGroups = [
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard', groups: [ 'undo', 'clipboard' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
		{ name: 'forms', groups: [ 'forms' ] },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'insert', groups: [ 'insert' ] },
		{ name: 'tools', groups: [ 'tools' ] },
		{ name: 'others', groups: [ 'others' ] },
		{ name: 'about', groups: [ 'about' ] },
		
	];
	//숨길 버튼
	config.removeButtons = 'Source,Save,NewPage,ExportPdf,Preview,Print,Templates,Cut,Copy,Paste,PasteText,PasteFromWord,Undo,Redo,Find,Replace,SelectAll,Scayt,Form,TextField,Textarea,Select,Button,ImageButton,HiddenField,CopyFormatting,RemoveFormat,NumberedList,BulletedList,Outdent,Indent,CreateDiv,BidiLtr,BidiRtl,Language,Anchor,Flash,Table,HorizontalRule,Smiley,SpecialChar,PageBreak,Iframe,Format,Maximize,ShowBlocks,About,Subscript,Superscript,Blockquote,Checkbox,Radio,Styles';
	config.enterMode = CKEDITOR.ENTER_BR;
	config.fillEmptyBlocks = false;

};

CKEDITOR.on('dialogDefinition', function( ev ){
	var dialog = ev.data.definition.dialog;
	var dialogName = ev.data.name;
    var dialogDefinition = ev.data.definition;
  
    switch (dialogName) {
        case 'image': // 이미지 속성창이 보일때 안보이게 하기 위해서.
        	
            //dialogDefinition.removeContents('info');
           	dialogDefinition.removeContents('Link');
            dialogDefinition.removeContents('advanced');
            
            
            dialog.on('show', function (obj) {
        		this.selectPage('Upload'); //업로드텝으로 시작
            });
            
            var infoTab = dialogDefinition.getContents('info');
            infoTab.remove('txtAlt'); //대체텍스트 제거
            infoTab.remove( 'txtBorder'); // 테두리 
            infoTab.remove( 'txtHSpace'); // 세로여백 
            infoTab.remove( 'txtVSpace'); // 가로여백 
            infoTab.remove( 'cmbAlign'); // 정렬 
            
            break;
            
            
    }
});
