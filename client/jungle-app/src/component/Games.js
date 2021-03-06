import React from "react";
import {Button, DropdownItem, Dropdown, DropdownButton} from "react-bootstrap";

class Games extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            activeGames: this.props.activeGames,
            completedGames: this.props.completedGames,
        };

    }

    async getGames(url, payload, headers){
        // event.preventDefault();
        this.props.getGames(url, payload, headers)
            .then(response =>
                this.props.updateActiveGames(response.activeGames)
                // console.log("GET GAMES RESPONSE: " + response.activeGames)
            );
    }

    componentDidMount() {
        // console.log
        this.getGames(this.props.apiConfig.url, "action=ViewUserGames&username=" + this.props.username + "&password=" + this.props.password, this.props.apiConfig.headers);
    }


    render() {
        // This is where active and inactive games can be chosen for either viewing or playing.

            const activeGamesList = this.props.activeGames.map((game) =>
                    <Dropdown.Item as="button" onClick={this.props.setSelectedGame} value={game}>
                        Game ID: {game}
                    </Dropdown.Item>);
            return (
                <div className={'GamesPage'}>
                    {/*<ul className={'list-group list-group-horizontal'}>*/}
                    {/*    {activeGamesList}*/}
                    {/*</ul>*/}
                    <Dropdown>
                        <Dropdown.Toggle id="dropdown-basic">
                            Games List
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                            {activeGamesList}
                        </Dropdown.Menu>
                    </Dropdown>
                    {/*<h2>Completed Games</h2>*/}
                    {/*<ul className={'list-group list-group-horizontal'}>*/}
                    {/*    {completedGamesList}*/}
                    {/*</ul>*/}

                </div>
            )
    }
}

export default Games;
