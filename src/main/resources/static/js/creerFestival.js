$(function () {
    $('#dateDebut').on('change', function () {
        $('#dateFin').attr('min', $('#dateDebut').val());
    });
    $('#dateFin').on('change', function () {
        $('#dateDebut').attr('max', $('#dateFin').val());
    });
});


$('.ui.form')
    .form({
        on: 'blur',
        fields: {
            nom: {
                identifier: 'nom',
                rules: [
                    {
                        type: 'empty',
                        prompt: 'Saisissez un nom'
                    }
                ]
            },
            dateDebut: {
                identifier: 'dateDebut',
                rules: [
                    {
                        type: 'empty',
                        prompt: 'Saisissez une date de début'
                    }
                ]
            },
            dateFin: {
                identifier: 'dateFin',
                rules: [
                    {
                        type: 'empty',
                        prompt: 'Saisissez une date de fin'
                    }
                ]
            },
            categorie: {
                identifier: 'categorie',
                rules: [
                    {
                        type: 'empty',
                        prompt: 'Saisissez une catégorie'
                    }
                ]
            }
        }
    });