/**
 * Select2 Component has some bugs but VilarikA can't work with 
 * newer versions right now.
 * 
 * This wrapper fixes some bugs and provides some auxiliar
 * functions.
 */
var Select2Wrapper = {};
Select2Wrapper.forceLabelUpdate = function($select2Element){
	if(!$select2Element || $select2Element.length < 1)
		throw new Error('Invalid given element');

	var $fakeCreatedSelect = $select2Element.next();
	var selectedText = $select2Element.find('option:selected').text();

	$fakeCreatedSelect.find('> a > span').text(selectedText);
};