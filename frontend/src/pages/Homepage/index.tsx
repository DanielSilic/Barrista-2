import React from 'react';
import { Card, Button, Container, Row, Col } from 'react-bootstrap';
import './homepage.css'

export function Homepage() {
    const tiles = [
        { id: 'kaffeesorten', title: 'Kaffeesorten', icon: '☕' },
        { id: 'tastings', title: 'Tastings', icon: '🍵' },
        { id: 'zubereitungsmethoden', title: 'Zubereitungsmethoden', icon: '🍶' },
        { id: 'roestereien', title: 'Röstereien', icon: '🔥' }
    ];

    return (
        <Container fluid>
            <Row className="justify-content-around">
                {tiles.map(tile => (
                    <Col xs={12} md={6} lg={3} className="my-2" key={tile.id}>
                        <Card className="text-center h-100">
                            <Card.Body>
                                <Card.Title>
                                    <span className="display-2">{tile.icon}</span>
                                </Card.Title>
                                <Button href={`/${tile.id}`} variant="primary" block>
                                    {tile.title}
                                </Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>
    );
}

