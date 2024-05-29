<div id="signup" class="main-section">
    <div class="col-md-6">
        <div class="card-custom p-5">
            <h4 class="text-center mb-4">Sign Up</h4>
            <form action="signup" method="post">
                <div class="input-group mb-3">
                    <span class="input-group-text" id="email"><i class="fas fa-envelope"></i></span>
                    <input type="email" class="form-control" placeholder="Email" aria-label="Email" aria-describedby="email" name="email" required>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="password"><i class="fas fa-lock"></i></span>
                    <input type="password" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="password" name="password" required>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="confirmPassword"><i class="fas fa-lock"></i></span>
                    <input type="password" class="form-control" placeholder="Confirm Password" aria-label="ConfirmPassword" aria-describedby="confirmPassword" name="confirmPassword" required>
                </div>
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary w-100">Sign Up</button>
                </div>
                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null && !error.isEmpty()) {
                %>
                    <div class="alert alert-danger mt-4" role="alert">
                        <%= error %>
                    </div>
                <% } %>
                <%
                    if (error != null && !error.isEmpty()) {
                %>
                    <script>
                        setTimeout(function() {
                            let errorMessage = document.querySelectorAll('.alert-danger');
                            errorMessage.forEach(function(element) {
                                element.style.display = 'none';
                            });
                        }, 4000);
                    </script>
                <% } %>
            </form>
        </div>
    </div>
</div>
