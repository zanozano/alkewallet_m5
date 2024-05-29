<div id="login" class="main-section">
    <div class="col-md-6">
        <div class="card-custom p-5">
            <h4 class="text-center mb-5">Login</h4>
            <form action="login" method="post">
                <div class="input-group mb-3">
                    <span class="input-group-text" id="email"><i class="fas fa-envelope"></i></span>
                    <input type="text" class="form-control" placeholder="email" aria-label="Email" aria-describedby="email" name="email">
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="password"><i class="fas fa-lock"></i></span>
                    <input type="password" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="password" name="password">
                </div>
                <div class="d-grid gap-2 mb-4">
                    <button type="submit" class="btn btn-primary w-100">Login</button>
                </div>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger errorMessage">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                <% if (request.getAttribute("empty") != null) { %>
                    <div class="alert alert-danger errorMessage">
                        <%= request.getAttribute("empty") %>
                    </div>
                <% } %>
                <script>
                    setTimeout(function() {
                        let errorMessage = document.querySelectorAll('.errorMessage');
                        errorMessage.forEach(function(element) {
                            element.style.display = 'none';
                        });
                    }, 4000);
                </script>
            </form>
            <div class="text-center d-flex flex-column">
                <p class="m-0">Don't have an account?</p>
                <a href="signup" class="btn btn-link">Create Account</a>
            </div>
        </div>
    </div>
</div>
