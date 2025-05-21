import React, { useState, useEffect } from 'react';

function UpdateServiceForm({ service, onClose, onSave }) {
    const [formData, setFormData] = useState({
        serviceid:'',
        title: '',
        description: '',
        basePrice: '',
        rating: '',
        discount: '',
        image: '',
        categoryId: 1
    });

    useEffect(() => {
        if (service) {
            setFormData(service);
        }
    }, [service]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        try {
            const response = await fetch(`http://localhost:8080/services/${formData.serviceid}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData),
            });
    
            if (!response.ok) {
                throw new Error("Failed to update service");
            }
    
            const updatedService = await response.json();
            onSave(updatedService); // Pass updated data to parent
            alert("Service updated successfully!");
            onClose(); // Close the form after successful update
        } catch (error) {
            console.error("Error updating service:", error);
            alert("Failed to update service");
        }
    };
    
    

    return (
        <div className="admin-dashboard">
            <div style={{ marginBottom: "20px" }}>
                <label htmlFor="categoryId">Select Category: </label>
                <select
                    id="categoryId"
                    name="categoryId"
                    value={formData.categoryId}
                    onChange={handleInputChange}
                >
                    <option value="1">Wedding Services</option>
                    <option value="2">Engagement Services</option>
                    <option value="3">Birthday Services</option>
                    <option value="4">Veg Catering Services</option>
                    <option value="5">NonVeg Catering Services</option>
                </select>
            </div>
            <div className="add-service-form">
                <h2>Update Service</h2>
                <form onSubmit={handleSubmit}>
                {/* <div className="form-group">
                        <label>ID:</label>
                        <input
                            type="text"
                            name="title"
                            value={formData.serviceid}
                            onChange={handleInputChange}
                        />
                    </div> */}
                    <div className="form-group">
                        <label>Title:</label>
                        <input
                            type="text"
                            name="title"
                            value={formData.title}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Description:</label>
                        <textarea
                            name="description"
                            value={formData.description}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Price:</label>
                        <input
                            type="text"
                            name="basePrice"
                            value={formData.basePrice}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Rating:</label>
                        <input
                            type="text"
                            name="rating"
                            value={formData.rating}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Discount:</label>
                        <input
                            type="text"
                            name="discount"
                            value={formData.discount}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Image URL:</label>
                        <input
                            type="text"
                            name="image"
                            value={formData.image}
                            onChange={handleInputChange}
                        />
                    </div>
                    <button type="submit" className="add-service-btn">
                        Update Service
                    </button>
                    <button type="button" onClick={onClose} className="add-service-btn">
                        Cancel
                    </button>
                </form>
            </div>
        </div>
    );
}

export default UpdateServiceForm;
