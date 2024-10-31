import React from 'react';
import Logout from "./Logout";
import AddParking from "./AddParking";

const Welcome = ({ user,onLogout }) => {

        if(!user){
            return <h1>Loading...</h1>  // Display a loading message until user data is fetched
        }
        return(
        <div>
            <h1>Welcome, {user.name}!</h1>
            <img src={user.profileImageUrl} alt={`${user.name}'s profile`} />
            <p>Email: {user.email}</p>
            <Logout onLogout={onLogout} />
            <AddParking userId={user.id} />
        </div>
    );
};

export default Welcome;
