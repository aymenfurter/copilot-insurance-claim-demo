document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('claimForm');
    const fileInput = document.getElementById('fileUpload');
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
    .then(data => populateForm(data))
    .catch(error => console.error('Error:', error))
    .finally(() => toggleLoader(false));
}

function populateForm(data) {
    document.getElementById('replyText').value = data.replyText || '';
    document.getElementById('carModel').value = data.carModel || '';
    document.getElementById('carMake').value = data.carMake || '';
    document.getElementById('licensePlateNumber').value = data.licensePlateNumber || '';
    document.getElementById('situationExplanation').value = data.situationExplanation || '';
    document.getElementById('damageType').value = data.damageType || '';
    document.getElementById('carInsuranceType').value = data.carInsuranceType || '';
    document.getElementById('estimatedCostCategory').value = data.estimatedCostCategory || '';

    // Enable form fields
    enableFormFields();
}

function enableFormFields() {
    const fields = document.querySelectorAll('#claimForm input, #claimForm select');
    fields.forEach(field => field.disabled = false);
}

function resetForm() {
    const form = document.getElementById('claimForm');
    form.reset();
    enableFormFields();
}

function submitClaim() {
    console.log('Claim submitted');
}

function toggleLoader(show) {
    const loader = document.querySelector('.loader');
    loader.style.display = show ? 'block' : 'none';
}
