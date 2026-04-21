import API from "./index";

export const getUsers = () => API.get("/users");

export const createUser = (data) =>
  API.post("/users", {
    username: data.username,
    email: data.email,
    password: data.password
  });

export const updateUser = (id, data) =>
  API.put(`/users/${id}`, data);

export const deleteUser = (id) =>
  API.delete(`/users/${id}`);