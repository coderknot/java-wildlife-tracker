$(document).ready(function() {

  $("input:radio[name='add-animal-type']").click(function () {

    if($(this).attr('id') == 'add-animal-type-endangered') {
      $('#add-animal-endangered').show();
    } else {
      $('#add-animal-endangered').hide();
    }

  });

});
