//jquery Plugins
/**
 * Mask phone of liminha...
 */
jQuery.fn.phoneMask = function() {
    return this.each(function() {
        $(this).bind('input propertychange', function() {
            var texto = $(this).val();
            texto = texto.replace(/[^\d]/g, '');
            if (texto.length > 0) {
                texto = "(" + texto;
                if (texto.length > 3) {
                    texto = [texto.slice(0, 3), ") ", texto.slice(3)].join('');
                }
                if (texto.length > 12) {
                    if (texto.length > 13)
                        texto = [texto.slice(0, 10), "-", texto.slice(10)].join('');
                    else
                        texto = [texto.slice(0, 9), "-", texto.slice(9)].join('');
                }
                if (texto.length > 15)
                    texto = texto.substr(0, 15);
            }
            $(this).val(texto);
        })
    });
};

jQuery.fn.localStorageField = function() {
    return this.each(function() {
        var $this = $(this);
        var isCheckbox = $this.is(":checkbox");
        $this.change(function() {
            if (isCheckbox) {
                localStorage.setItem($this.attr("id"), $this.is(":checked"));
            } else {
                localStorage.setItem($this.attr("id"), $this.val());
            }
        });
        var value = localStorage.getItem($this.attr("id"));
        if (isCheckbox) {
            if (value === 'true') {
                $this.attr("checked", true);
            }
        } else {
            // lucas alterou aqui 23/08/2016  criou o if para tratar campo miltiple
            // antes erá só $this.val(value);
            // alert ("vaiiiiii antes " + $this.attr("id") + "  " + value)
            if(value){
                value = value.split(',');
                if (value.length === 1) {
                    $this.val(value[0]);   
                } else {
                    $this.val(value);
                }
            }
        }
    });
};
jQuery.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
jQuery.fn.valueToEnvitoment = function(allow_null, callback) {
    var field = this;
    $(field).change(function() {
        var value = $(field).val();
        var name = "";
        if ($(field).is("select")) {
            name = $("option:selected", field).html();
        };
        Envitoment[$(field).data("env-value") || $(field).attr("id")] = value;
        Envitoment[$(field).data("env-name") || $(field).attr("id") + "_NAME"] = name;
        Envitoment.reprocess();
    });
};