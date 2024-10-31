class AuthService {
  login() {
    window.location.href = "/oauth2/authorization/google"; // Redirect to Google OAuth login
  }

  logout() {
    window.localStorage.removeItem("user"); // Clear user data
  }

  getCurrentUser() {
    return JSON.parse(window.localStorage.getItem("user")); // Get the current logged-in user
  }
}

export default new AuthService();
