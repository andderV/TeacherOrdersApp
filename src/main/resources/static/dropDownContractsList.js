$(document).ready(function () {
    $('#grocery').change(
        function () {
            $.getJSON("http://localhost/units/loadUnits", {
                id: $(this).val(),
                ajax: 'true'
            }, function (data) {
                var html = "";
                var len = data.length;
                for (var i = 0; i < len; i++) {
                    html += '<option value="' + data[i].measureUnitId + '">'
                        + data[i].measureUnitName + '</option>';
                }
                html += '</option>';
                $('#measureUnit').html(html);
            });
        });
});