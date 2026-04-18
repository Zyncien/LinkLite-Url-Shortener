import { useState } from "react";
import "./App.css";

function Login({ setUser }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    const res = await fetch("http://localhost:8082/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });

    if (res.ok) {
      setUser(username);
    } else {
      alert("Login failed");
    }
  };

  const handleRegister = async () => {
    await fetch("http://localhost:8082/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });

    alert("Registered! Now login.");
  };

  return (
    <div className="container">
      <div className="card">
        <h1 className="title">LinkLite 🔗</h1>
        <p className="subtitle">Smart URL Shortener</p>

        <input
          className="input"
          placeholder="Username"
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          className="input"
          type="password"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button className="button" onClick={handleLogin}>
          Login
        </button>

        <button className="button secondary" onClick={handleRegister}>
          Register
        </button>
      </div>
    </div>
  );
}

export default Login;