$(function() {
  $('#date').datepicker({weekStart: 1, dateFormat: "dd-mm-yy"});
  $('#date').datepicker('setValue', new Date());
});