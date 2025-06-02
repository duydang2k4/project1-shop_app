export interface Product {
    createdAt: Date;
    updatedAt: Date;
    id: number;
    name: string;
    price: number;
    thumbnail: string;
    description: string;
    categoryId: {
        id: number;
        name: string;
    };
    url: string;
}