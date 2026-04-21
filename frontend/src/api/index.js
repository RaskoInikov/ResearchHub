import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080",
});

API.interceptors.response.use(
  res => res,
  err => {
    console.error("API ERROR:", err.response?.data);
    return Promise.reject(err);
  }
);

export default API;