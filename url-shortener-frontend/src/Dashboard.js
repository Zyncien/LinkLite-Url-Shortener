import { useState, useEffect } from "react";
import "./App.css";

function Dashboard({ user, setUser }) {
  const [url, setUrl] = useState("");
  const [title, setTitle] = useState("");
  const [history, setHistory] = useState([]);

  const fetchHistory = async () => {
    const res = await fetch(
      `http://localhost:8082/all?username=${user}`
    );
    const data = await res.json();
    setHistory(data.reverse());
  };

  useEffect(() => {
    fetchHistory();
  }, []);

  const handleShorten = async () => {
    if (!url.startsWith("http")) {
      alert("Enter valid URL");
      return;
    }

    await fetch(
      `http://localhost:8082/shorten?url=${url}&username=${user}&title=${title}`
    );

    setUrl("");
    setTitle("");
    fetchHistory();
  };

  const deleteLink = async (id) => {
    await fetch(`http://localhost:8082/delete/${id}`, {
      method: "DELETE",
    });
    fetchHistory();
  };

  const copyToClipboard = (text) => {
    navigator.clipboard.writeText(text);
  };

  return (
    <>
      <div className="navbar">
        <h2>LinkLite 🔗</h2>

        <div className="right-nav">
          <span className="username">{user}</span>
          <button className="logout" onClick={() => setUser(null)}>
            Logout
          </button>
        </div>
      </div>

      <div className="container">
        <div className="card">
          <h2>Shorten URL</h2>

          <input
            className="input"
            placeholder="Title (optional)"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />

          <input
            className="input"
            placeholder="Paste URL..."
            value={url}
            onChange={(e) => setUrl(e.target.value)}
          />

          <button className="button" onClick={handleShorten}>
            Shorten URL
          </button>
        </div>

        <div className="history">
          <h2>Your Links</h2>

          {history.map((item) => (
            <div key={item.id} className="history-item">
              <h3>{item.title}</h3>

              <p>{item.originalUrl}</p>

              <a
                href={`http://localhost:8082/${item.shortCode}`}
                target="_blank"
                rel="noreferrer"
              >
                http://localhost:8082/{item.shortCode}
              </a>

              <br />

              <button
                className="copy-btn"
                onClick={() =>
                  copyToClipboard(
                    `http://localhost:8082/${item.shortCode}`
                  )
                }
              >
                Copy
              </button>

              <button
                className="logout"
                onClick={() => deleteLink(item.id)}
                style={{ marginLeft: "10px" }}
              >
                Delete
              </button>

              <p>Clicks: {item.clickCount}</p>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default Dashboard;