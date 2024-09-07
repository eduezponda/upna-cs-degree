import React, { useState } from 'react';

const ContactSection = () => {
  const [email, setEmail] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    alert(`Thank you! Your email ${email} has been submitted.`);
    setEmail('');
  };

  return (
    <div className="contact-section">
      <h2>CONTACT US</h2>
      <p>Any question? Talk to our developers</p>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          placeholder="Enter your email..."
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default ContactSection;
