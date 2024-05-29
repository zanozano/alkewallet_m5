<div class="modal" tabindex="-1" id="modalWithdraw">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h3 class="modal-title">Withdraw</h3>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="d-flex flex-column justify-content-center align-items-center">
          <img src="img/deposit-withdraw.png" alt="Coin Image" class="img-fluid" width="200px" height="200px">
          <p class="text-center">Please complete the information below to proceed with the action.</p>
        </div>
        <form id="withdrawForm" action="/withdraw" method="post">
          <div class="input-group mb-3">
            <span class="input-group-text"><i class="fas fa-arrow-right-arrow-left"></i></span>
            <select id="currency" class="form-control" aria-label="Currency" aria-describedby="currency" name="currency">
              <option selected>Select account</option>
              <option value="USD">USD</option>
              <option value="CNY">CNY</option>
              <option value="EUR">EUR</option>
              <option value="THB">THB</option>
            </select>
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text"><i class="fas fa-dollar-sign"></i></span>
            <input id="amount" type="number" class="form-control" placeholder="Enter amount" aria-label="Amount" aria-describedby="amount" name="amount">
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-primary">Confirm</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>