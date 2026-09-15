# Hệ Thống Microservices Quản Lý Sản Phẩm & Danh Mục

Dự án Microservices hoàn chỉnh gồm 5 dịch vụ, kết nối và giao tiếp qua Service Discovery (Eureka), Config Server tập trung, API Gateway và OpenFeign.

---

## 1. Kiến Trúc Hệ Thống

| Dịch Vụ | Cổng (Port) | Chức Năng |
| **discovery-server** | `8761` | Eureka Server - Quản lý và phát hiện dịch vụ |
| **config-server** | `8888` | Spring Cloud Config Server - Cấp phát cấu hình tập trung từ `config-repo` |
| **category-service** | `8081` | Quản lý danh mục sản phẩm (H2 Database) |
| **product-service** | `8082` | Quản lý sản phẩm, gọi sang Category Service qua OpenFeign |
| **api-gateway** | `8080` | Điểm tiếp nhận request tập trung duy nhất, cân bằng tải động `lb://` |

---

## 2. Thứ Tự Khởi Chạy

1. **Discovery Server:**
   ```bash
   cd discovery-server
   ./gradlew bootRun
   ```
2. **Config Server:**
   ```bash
   cd config-server
   ./gradlew bootRun
   ```
3. **Category Service:**
   ```bash
   cd category-service
   ./gradlew bootRun
   ```
4. **Product Service:**
   ```bash
   cd product-service
   ./gradlew bootRun
   ```
5. **API Gateway:**
   ```bash
   cd api-gateway
   ./gradlew bootRun
   ```

*(Kiểm tra dashboard Eureka tại: http://localhost:8761 để thấy 3 service đã UP)*

---

## 3. Kịch Bản Kiểm Thử (Gọi Qua Cổng Duy Nhất 8080 Của API Gateway)

### Case 1: Lấy danh sách danh mục
**Request:** `GET http://localhost:8080/api/categories`
**Response (200 OK):** Trả về 4 danh mục mẫu có sẵn (Electronics, Smartphones, Audio, Accessories).

### Case 2: Lấy chi tiết danh mục theo ID
**Request:** `GET http://localhost:8080/api/categories/1`
**Response (200 OK):** Trả về thông tin danh mục ID 1 (`Electronics`).

### Case 3: Lấy danh sách sản phẩm
**Request:** `GET http://localhost:8080/api/products`
**Response (200 OK):** Trả về danh sách sản phẩm có sẵn.

### Case 4: Tạo sản phẩm mới thành công (Xác thực qua OpenFeign)
**Request:** `POST http://localhost:8080/api/products`
**Header:** `Content-Type: application/json`
**Body:**
  ```json
  {
    "name": "MacBook Air M3",
    "price": 1299.0,
    "categoryId": 1
  }
  ```
**Response (201 Created):** `product-service` gọi FeignClient sang `category-service` kiểm tra ID 1 tồn tại và lưu thành công.

### Case 5: Tạo sản phẩm thất bại khi Danh mục không tồn tại
**Request:** `POST http://localhost:8080/api/products`
**Header:** `Content-Type: application/json`
**Body:**
  ```json
  {
    "name": "Alien Device",
    "price": 99.0,
    "categoryId": 9999
  }
  ```
**Response (400 Bad Request):** Báo lỗi danh mục với ID 9999 không tồn tại.

