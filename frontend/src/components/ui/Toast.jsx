import { createContext, useContext, useState } from "react";

const ToastContext = createContext();

export const useToast = () => useContext(ToastContext);

export const ToastProvider = ({ children }) => {
  const [message, setMessage] = useState(null);

  const show = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(null), 3000);
  };

  return (
    <ToastContext.Provider value={{ show }}>
      {children}

      {message && (
        <div style={{
          position: "fixed",
          bottom: "20px",
          right: "20px",
          background: "#333",
          color: "white",
          padding: "10px"
        }}>
          {message}
        </div>
      )}
    </ToastContext.Provider>
  );
};