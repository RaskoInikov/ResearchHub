import axios from "axios";

const API = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "/api",
});

API.interceptors.response.use(
  res => res,
  err => {
    console.error("API ERROR:", err.response?.data);
    return Promise.reject(err);
  }
);

export default API;