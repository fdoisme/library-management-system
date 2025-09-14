
# Library Management System

## 🔓 Endpoint Tanpa Authentication

```
  POST	/auth/login
```
```
  GET	/buku/find-all
```
```
  GET	/buku/find-by-id/{id}
```
```
  GET	/lokasi/find-all
```
```
  GET	/lokasi/find-by-id/{id}
```
## 🔐 Endpoint Dengan Authentication dan Authorization (Role: Pustakawan)
```
  DELETE    /user/delete/{id}
```
```
  GET	    /user/find-all
```
```
  DELETE	/peminjaman/delete/{id}
```
```
  GET	    /peminjaman/find-all
```
```
  POST	    /lokasi/add
```
```
  DELETE	/lokasi/delete/{id}
```
```
  PUT	    /lokasi/edit
```
```
  POST	    /buku/add
```
```
  DELETE	/buku/delete/{id}
```
```
  PUT	    /buku/edit
```


### Flowchart Peminjaman Buku
``` mermaid
---
config:
  layout: dagre
---
flowchart TD
    A(["Start"]) --> B["Login?"]
    B -- YA --> C["Submit Request Body"]
    C --> D["Valid Request?"]
    D -- YA --> E["User Sedang Meminjam?"]
    E -- TIDAK --> F["Buku Sedang Dipinjam?"]
    F -- TIDAK --> G["Save Data Peminjaman"]
    G --> H["Menambahkan nilai di field kapasitasTersedia pada entity lokasi"]
    H --> I["Update Data Lokasi Buku menjadi null karena buku berhasi dipinjam"]
    I --> J(["End"])
    D -- TIDAK --> J
    E -- YA --> J
    F -- YA --> J
    B@{ shape: diam}
    C@{ shape: rect}
    D@{ shape: diam}
    E@{ shape: diam}
    F@{ shape: diam}
    G@{ shape: rect}
    H@{ shape: rect}
    I@{ shape: rect}
```

### Flowchart Pengembalian Buku
``` mermaid
---
config:
  layout: dagre
---
flowchart TD
    A(["Start"]) --> B["Login?"]
    B -- YA --> C["Submit Request Body"]
    C --> D["Valid Request?"]
    D -- YA --> E["Apakah User di Data Peminjaman sama dengan User yang mengembalikan?"]
    E -- YA --> G["Update Data Peminjaman returnDate menjadi tanggal pengembalian"]
    G --> H["Mencari Lokasi dengan kapasitasTersedia > 0 dan mengurangi nilainya"]
    H --> I["Menjadikan Lokasi tersebut sebagai nilai lokasi buku yang dikembalikan"]
    I --> J(["End"])
    D -- TIDAK --> J
    E -- TIDAK --> J
    B@{ shape: diam}
    C@{ shape: rect}
    D@{ shape: diam}
    E@{ shape: diam}
    G@{ shape: rect}
    H@{ shape: rect}
    I@{ shape: rect}
```
