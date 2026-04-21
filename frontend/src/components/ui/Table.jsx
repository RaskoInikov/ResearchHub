import { useState } from "react";

const Table = ({ data, columns }) => {
  const [sortKey, setSortKey] = useState(null);
  const [page, setPage] = useState(0);
  const pageSize = 5;

  let sorted = [...data];

  if (sortKey) {
    sorted.sort((a, b) =>
      a[sortKey]?.toString().localeCompare(b[sortKey]?.toString())
    );
  }

  const totalPages = Math.ceil(sorted.length / pageSize);
  const paginated = sorted.slice(page * pageSize, (page + 1) * pageSize);

  return (
    <div>
      <table className="table-modern">
        <thead>
          <tr>
            {columns.map(col => (
              <th key={col.key} onClick={() => setSortKey(col.key)}>
                {col.label}
              </th>
            ))}
          </tr>
        </thead>

        <tbody>
          {paginated.map(row => (
            <tr key={row.id}>
              {columns.map(col => (
                <td key={col.key}>
                  {col.render ? col.render(row) : row[col.key]}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>

      {/* PAGINATION */}
      <div className="pagination">
        <button
          className="page-btn"
          disabled={page === 0}
          onClick={() => setPage(p => p - 1)}
        >
          ← Prev
        </button>

        <span className="page-info">
          Page {page + 1} of {totalPages}
        </span>

        <button
          className="page-btn"
          disabled={page >= totalPages - 1}
          onClick={() => setPage(p => p + 1)}
        >
          Next →
        </button>
      </div>
    </div>
  );
};

export default Table;