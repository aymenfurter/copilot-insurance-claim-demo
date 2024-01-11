document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('claimForm');
    const fileInput = document.getElementById('fileUpload');
    const replyTextContainer = document.getElementById('replyTextContainer');
    const submitButton = form.querySelector('button[type="submit"]');
    const resetButton = form.querySelector('button[type="reset"]');
    const loader = document.createElement('div');
    loader.className = 'loader';
    loader.style.display = 'none';
    form.appendChild(loader);

    fileInput.addEventListener('change', function(event) {
        if (event.target.files.length) {
            handleSubmitFile(event.target.files[0]);
        }
    });

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        submitClaim();
    });

    resetButton.addEventListener('click', function() {
        resetForm();
    });
});

function handleSubmitFile(file) {
    const formData = new FormData();
    formData.append('file', file);

    toggleLoader(true);

    fetch('/api/image-analysis/upload', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        populateForm(data);
        displayReplyText(data.replyText);
    })
    .catch(error => console.error('Error:', error))
    .finally(() => toggleLoader(false));
}

function populateForm(data) {
    document.getElementById('carModel').value = data.carModel || '';
    document.getElementById('carMake').value = data.carMake || '';
    document.getElementById('licensePlateNumber').value = data.licensePlateNumber || '';
    document.getElementById('situationExplanation').value = data.situationExplanation || '';
    document.getElementById('damageType').value = data.damageType || '';
    document.getElementById('carInsuranceType').value = data.carInsuranceType || '';
    document.getElementById('estimatedCostCategory').value = data.estimatedCostCategory || '';

    enableFormFields();
}

function displayReplyText(text) {
    const replyText = document.getElementById('replyText');
    const replyTextContainer = document.getElementById('replyTextContainer');
    replyText.textContent = text;

    if (text) {
        replyTextContainer.style.opacity = '1';
    } else {
        replyTextContainer.style.opacity = '0';
    }
}


function enableFormFields() {
    const fields = document.querySelectorAll('#claimForm input, #claimForm select, #claimForm textarea');
    fields.forEach(field => field.disabled = false);
}

function resetForm() {
    const form = document.getElementById('claimForm');
    form.reset();
    enableFormFields();
    document.getElementById('replyText').textContent = '';
}

function submitClaim() {
    console.log('Claim submitted');
    // Implement actual submission logic here
}

function toggleLoader(show) {
    const loader = document.querySelector('.loader');
    loader.style.display = show ? 'block' : 'none';
}
