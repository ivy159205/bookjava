const API_BASE_URL = 'http://localhost:8080/api/books'; // Cổng mặc định của Spring Boot
const booksTableBody = document.getElementById('booksTableBody');
const bookIdInput = document.getElementById('bookId');
const titleInput = document.getElementById('title');
const authorInput = document.getElementById('author');
const isbnInput = document.getElementById('isbn');
const publicationYearInput = document.getElementById('publicationYear');
const saveBookBtn = document.getElementById('saveBookBtn');
const clearFormBtn = document.getElementById('clearFormBtn');

// Các phần tử mới cho tìm kiếm
const searchKeywordInput = document.getElementById('searchKeyword');
const searchBtn = document.getElementById('searchBtn');
const clearSearchBtn = document.getElementById('clearSearchBtn');

// Hàm tải tất cả sách (hoặc tìm kiếm sách)
async function fetchBooks(keyword = '') {
    let url = API_BASE_URL;
    if (keyword) {
        url += `?keyword=${encodeURIComponent(keyword)}`;
    }
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const books = await response.json();
        renderBooks(books);
    } catch (error) {
        console.error('Lỗi khi tải/tìm kiếm sách:', error);
        alert('Không thể tải danh sách sách. Vui lòng kiểm tra console.');
    }
}

// Hàm hiển thị sách ra bảng
function renderBooks(books) {
    booksTableBody.innerHTML = ''; // Xóa nội dung cũ
    if (books.length === 0) {
        const row = booksTableBody.insertRow();
        row.innerHTML = `<td colspan="6" style="text-align: center;">Không tìm thấy sách nào.</td>`;
        return;
    }
    books.forEach(book => {
        const row = booksTableBody.insertRow();
        row.innerHTML = `
            <td>${book.id}</td>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.isbn}</td>
            <td>${book.publicationYear || ''}</td>
            <td>
                <button onclick="editBook(${book.id})">Sửa</button>
                <button class="delete-btn" onclick="deleteBook(${book.id})">Xóa</button>
            </td>
        `;
    });
}

// Hàm lưu (thêm/cập nhật) sách
saveBookBtn.addEventListener('click', async () => {
    const id = bookIdInput.value;
    const book = {
        title: titleInput.value,
        author: authorInput.value,
        isbn: isbnInput.value,
        publicationYear: publicationYearInput.value ? parseInt(publicationYearInput.value) : null
    };

    if (!book.title || !book.author || !book.isbn) {
        alert('Vui lòng điền đầy đủ tiêu đề, tác giả và ISBN.');
        return;
    }

    try {
        let response;
        if (id) {
            // Cập nhật sách
            response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(book)
            });
        } else {
            // Thêm sách mới
            response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(book)
            });
        }

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }

        clearForm();
        fetchBooks(); // Tải lại danh sách sau khi lưu
    } catch (error) {
        console.error('Lỗi khi lưu sách:', error);
        alert(`Không thể lưu sách. Vui lòng kiểm tra console. Lỗi: ${error.message}`);
    }
});

// Hàm chỉnh sửa sách (điền dữ liệu vào form)
async function editBook(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const book = await response.json();
        bookIdInput.value = book.id;
        titleInput.value = book.title;
        authorInput.value = book.author;
        isbnInput.value = book.isbn;
        publicationYearInput.value = book.publicationYear || '';
    } catch (error) {
        console.error('Lỗi khi tải sách để chỉnh sửa:', error);
        alert('Không thể tải thông tin sách để chỉnh sửa. Vui lòng kiểm tra console.');
    }
}

// Hàm xóa sách
async function deleteBook(id) {
    if (confirm('Bạn có chắc chắn muốn xóa sách này không?')) {
        try {
            const response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            fetchBooks(); // Tải lại danh sách sau khi xóa
        } catch (error) {
            console.error('Lỗi khi xóa sách:', error);
            alert('Không thể xóa sách. Vui lòng kiểm tra console.');
        }
    }
}

// Hàm xóa form
clearFormBtn.addEventListener('click', clearForm);

function clearForm() {
    bookIdInput.value = '';
    titleInput.value = '';
    authorInput.value = '';
    isbnInput.value = '';
    publicationYearInput.value = '';
}

// Event listener cho nút tìm kiếm
searchBtn.addEventListener('click', () => {
    const keyword = searchKeywordInput.value.trim();
    fetchBooks(keyword);
});

// Event listener cho nút xóa tìm kiếm
clearSearchBtn.addEventListener('click', () => {
    searchKeywordInput.value = ''; // Xóa từ khóa tìm kiếm
    fetchBooks(); // Tải lại tất cả sách
});

// Tải sách khi trang được load
document.addEventListener('DOMContentLoaded', fetchBooks);