import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <div className="navbar">
      <nav>
        <Link className="nav-link" to="/">
          Accueil
        </Link>
        <Link className="nav-link" to="/formurl">
          Formulaire Web Crawling
        </Link>
      </nav>
    </div>
  );
};

export default Navbar;
<nav>
  <ul>
    <li>
      <a href=""></a>
    </li>
  </ul>
</nav>;
