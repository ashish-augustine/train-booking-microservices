import React, { useState, useEffect } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Link,
  Navigate,
} from "react-router-dom";
import api from "./api";

function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const register = async () => {
    try {
      await api.post("/users/register", { username, password });
      alert("Registered! Please login.");
    } catch {
      alert("Registration failed");
    }
  };

  return (
    <div>
      <h2>Register</h2>
      <input
        placeholder="Username"
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        placeholder="Password"
        type="password"
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={register}>Register</button>
    </div>
  );
}

function Login({ setLoggedIn }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const login = async () => {
    try {
      const res = await api.post("/users/login", { username, password });
      localStorage.setItem("token", res.data.token);
      setLoggedIn(true);
    } catch {
      alert("Login failed");
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <input
        placeholder="Username"
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        placeholder="Password"
        type="password"
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={login}>Login</button>
    </div>
  );
}

function Trains() {
  const [trains, setTrains] = useState([]);

  useEffect(() => {
    api.get("/trains").then((res) => setTrains(res.data));
  }, []);

  const bookTrain = async (trainId) => {
    try {
      await api.post(`/booking/${trainId}`);
      alert("Booked successfully");
    } catch (e) {
      alert("Booking failed: " + e.response.data);
    }
  };

  return (
    <div>
      <h2>Trains</h2>
      {trains.map((train) => (
        <div key={train.id}>
          {train.name} — Total Seats: {train.totalSeats}{" "}
          <button onClick={() => bookTrain(train.id)}>Book</button>
        </div>
      ))}
    </div>
  );
}

function Tickets() {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    api.get("/tickets").then((res) => setTickets(res.data));
  }, []);

  const processPayment = async (trainId) => {
    try {
      await api.post("/payments/charge", {
        amount: 2000, // cents
        source: "tok_visa", // token
        description: `Booking for train ${trainId}`,
      });
      alert("Payment processed successfully");
    } catch (e) {
      alert("Payment failed: " + e.response.data);
    }
  };

  return (
    <div>
      <h2>Your Tickets</h2>
      {tickets.map((t) => (
        <div key={t.id}>
          Train: {t.trainId} - Seat No: {t.seatNumber} - Status: {t.status}
          <button onClick={() => processPayment(t.id)}>Pay</button>
        </div>
      ))}
    </div>
  );
}

function App() {
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    let token = localStorage.getItem("token");
    if (token) {
      setLoggedIn(true);
    }
  }, []);

  if (!loggedIn) {
    return (
      <Router>
        <nav>
          <Link to="/register">Register</Link> | <Link to="/login">Login</Link>
        </nav>
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login setLoggedIn={setLoggedIn} />} />
          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </Router>
    );
  }

  return (
    <Router>
      <nav>
        <Link to="/trains">Trains</Link> | <Link to="/tickets">Tickets</Link>
      </nav>
      <Routes>
        <Route path="/trains" element={<Trains />} />
        <Route path="/tickets" element={<Tickets />} />
        <Route path="*" element={<Navigate to="/trains" />} />
      </Routes>
    </Router>
  );
}

export default App;
