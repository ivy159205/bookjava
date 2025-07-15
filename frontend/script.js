const API_URL = 'http://localhost:8080/api/products';

document.addEventListener('DOMContentLoaded', () => {
    fetchProducts(); // Tải tất cả sản phẩm khi trang được load
});

/**
 * Lấy và hiển thị danh sách sản phẩm dựa trên các tham số tìm kiếm.
 * @param {string} name - Từ khóa tìm kiếm theo tên (tùy chọn).
 * @param {number} price - Giá tìm kiếm (tùy chọn).
 */
async function fetchProducts(name = '', price = null) {
    let url = new URL(API_URL); // Sử dụng đối tượng URL để dễ dàng thêm tham số

    if (name) {
        url.searchParams.append('name', name);
    }
    if (price !== null) {
        url.searchParams.append('price', price);
    }
    
    try {
        const response = await fetch(url.toString());
        if (!response.ok) {
            throw new Error(`Lỗi HTTP: ${response.status}`);
        }
        const products = await response.json();
        renderProducts(products);
    } catch (error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
        alert('Không thể tải danh sách sản phẩm. Vui lòng kiểm tra kết nối hoặc tham số tìm kiếm.');
        renderProducts([]); // Xóa bảng nếu có lỗi
    }
}

/**
 * Thực hiện tìm kiếm sản phẩm dựa trên input của người dùng (tên và/hoặc giá).
 */
function performSearch() {
    const searchName = document.getElementById('searchName').value;
    const searchPrice = document.getElementById('searchPrice').value;

    const priceValue = searchPrice ? parseFloat(searchPrice) : null;

    // Gọi hàm fetchProducts với các tham số tìm kiếm
    fetchProducts(searchName, priceValue);
}

/**
 * Tìm kiếm sản phẩm theo ID cụ thể.
 */
async function searchProductById() {
    const searchIdInput = document.getElementById('searchId');
    const id = searchIdInput.value;

    if (!id) {
        alert('Vui lòng nhập ID sản phẩm để tìm kiếm.');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) {
            if (response.status === 404) {
                alert(`Không tìm thấy sản phẩm với ID: ${id}`);
                renderProducts([]); // Xóa bảng nếu không tìm thấy
                return;
            }
            throw new Error(`Lỗi HTTP: ${response.status}`);
        }
        const product = await response.json();
        renderProducts([product]); // Hiển thị chỉ sản phẩm tìm được
    } catch (error) {
        console.error('Lỗi khi tìm kiếm sản phẩm theo ID:', error);
        alert('Không thể tìm kiếm sản phẩm theo ID. Vui lòng thử lại.');
    } finally {
        // searchIdInput.value = ''; // Tùy chọn: bạn có thể xóa ID sau khi tìm kiếm
    }
}

/**
 * Hiển thị danh sách sản phẩm lên bảng HTML.
 * @param {Array<Object>} products - Mảng các đối tượng sản phẩm.
 */
function renderProducts(products) {
    const tableBody = document.querySelector('#productTable tbody');
    tableBody.innerHTML = ''; // Xóa các hàng cũ

    if (!products || products.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center;">Không có sản phẩm nào được tìm thấy.</td></tr>';
        return;
    }

    products.forEach(product => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${product.id}</td>
            <td data-label="Tên sản phẩm">${product.name}</td>
            <td data-label="Giá tiền">${product.price}</td>
            <td data-label="Số lượng">${product.quantity}</td>
            <td data-label="Mô tả">${product.description || ''}</td>
            <td>
                <button onclick="editProduct(${product.id})">Sửa</button>
                <button class="delete-btn" onclick="deleteProduct(${product.id})">Xóa</button>
            </td>
        `;
    });
}

/**
 * Lưu (thêm mới hoặc cập nhật) một sản phẩm.
 */
async function saveProduct() {
    const id = document.getElementById('productId').value;
    const name = document.getElementById('productName').value;
    const price = parseFloat(document.getElementById('productPrice').value);
    const quantity = parseInt(document.getElementById('productQuantity').value);
    const description = document.getElementById('productDescription').value;

    if (!name || isNaN(price) || isNaN(quantity)) {
        alert('Vui lòng điền đầy đủ Tên sản phẩm, Giá tiền và Số lượng.');
        return;
    }

    const product = { name, price, quantity, description };

    let method = 'POST';
    let url = API_URL;

    if (id) {
        method = 'PUT';
        url = `${API_URL}/${id}`;
    }

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        });

        if (!response.ok) {
            throw new Error(`Lỗi HTTP: ${response.status}`);
        }

        clearForm();
        fetchProducts(); // Tải lại danh sách sau khi lưu
    } catch (error) {
        console.error('Lỗi khi lưu sản phẩm:', error);
        alert('Không thể lưu sản phẩm. Vui lòng thử lại.');
    }
}

/**
 * Lấy thông tin sản phẩm để điền vào form chỉnh sửa.
 * @param {number} id - ID của sản phẩm cần chỉnh sửa.
 */
async function editProduct(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        const product = await response.json();

        document.getElementById('productId').value = product.id;
        document.getElementById('productName').value = product.name;
        document.getElementById('productPrice').value = product.price;
        document.getElementById('productQuantity').value = product.quantity;
        document.getElementById('productDescription').value = product.description || '';
    } catch (error) {
        console.error('Lỗi khi lấy thông tin sản phẩm để sửa:', error);
        alert('Không thể tải thông tin sản phẩm để sửa.');
    }
}

/**
 * Xóa một sản phẩm.
 * @param {number} id - ID của sản phẩm cần xóa.
 */
async function deleteProduct(id) {
    if (!confirm('Bạn có chắc chắn muốn xóa sản phẩm này không?')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`Lỗi HTTP: ${response.status}`);
        }

        fetchProducts(); // Tải lại danh sách sau khi xóa
    } catch (error) {
        console.error('Lỗi khi xóa sản phẩm:', error);
        alert('Không thể xóa sản phẩm. Vui lòng thử lại.');
    }
}

/**
 * Xóa trắng form thêm/sửa sản phẩm.
 */
function clearForm() {
    document.getElementById('productId').value = '';
    document.getElementById('productName').value = '';
    document.getElementById('productPrice').value = '';
    document.getElementById('productQuantity').value = '';
    document.getElementById('productDescription').value = '';
}

/**
 * Xóa các trường tìm kiếm và tải lại tất cả sản phẩm.
 */
function clearSearch() {
    document.getElementById('searchName').value = '';
    document.getElementById('searchPrice').value = '';
    document.getElementById('searchId').value = '';
    fetchProducts(); // Tải lại tất cả sản phẩm
}