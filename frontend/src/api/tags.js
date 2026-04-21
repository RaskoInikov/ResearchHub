import API from "./index";

export const getTags = () => API.get("/tags");
export const createTag = (data) => API.post("/tags", data);
export const updateTag = (id, data) => API.put(`/tags/${id}`, data);
export const deleteTag = (id) => API.delete(`/tags/${id}`);