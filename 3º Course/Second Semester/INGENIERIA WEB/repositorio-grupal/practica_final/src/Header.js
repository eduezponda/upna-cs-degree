import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => (
  <header className="header">
    <nav className="navigation">
      <div className="logo">BOOKREALM</div>
      <div className="menu">
        <Link to="/about">About us</Link>
        <Link to="/blog">Blog</Link>
        <Link to="/help">Help</Link>
      </div>
    </nav>
  </header>
);

export default Header;
